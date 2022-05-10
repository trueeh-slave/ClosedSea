package co.edu.unbosque.testingproject.resources;

import co.edu.unbosque.testingproject.dto.Nft;
import co.edu.unbosque.testingproject.dto.User;
import co.edu.unbosque.testingproject.services.NftService;
import co.edu.unbosque.testingproject.services.UserService;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.james.mime4j.dom.Multipart;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static co.edu.unbosque.testingproject.services.UserService.getUsers;

@Path("/users/{username}/collections/{collection}/nfts")
public class NftResource {


    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "uploads";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserFiles(@PathParam("username") String username, @PathParam("collection") String collection){
        UserService userService = new UserService();
        Optional<List<Nft>> nftList = null;

        try{
            List<Nft> nfts = new ArrayList<Nft>();
            nftList = userService.getUserNfts();

            for(Nft nft : nftList.get()){
                if(nft.getAuthor().equals(username) /*Agregar lugar para las colecciones*/){
                    nft.setPath(UPLOAD_DIRECTORY + File.separator + nft.getPath());
                    nfts.add(nft);
                }
            }
            return Response.ok().entity(nfts).build();

        } catch (IOException e){
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)

    public Response uploadPiece(MultipartFormDataInput input){
        NftService nftService = new NftService();

        try{
            String username = input.getFormDataPart("author",String.class,null); //Cambiar despues por email
            //Agregar cuando este el sistema de colleciones
            String title = input.getFormDataPart("title",String.class,null);
            String fcoins = input.getFormDataPart("fcoins",String.class,null);

            List<User> userList = getUsers();
            User foundUser = userList.stream().filter(user -> username.equals(user.getUsername())).findFirst().orElse(null);
            Map<String, List<InputPart>> formPart = input.getFormDataMap();
            List<InputPart> inputParts = formPart.get("file");

            for(InputPart inputPart : inputParts){
                try{
                    String indetifier = new NftService().generateRandomString(8);
                    String fileName = indetifier+"|"+title;

                    InputStream inputStream = inputPart.getBody(InputStream.class,null);
                    saveImage(inputStream, fileName, context);

                    NftService.createPiece(title,username,fcoins,fileName,context.getRealPath("")+File.separator);
                } catch (IOException e){
                    return Response.serverError().build();
                }
            }
        } catch (IOException e){
            return Response.serverError().build();
        }
        return Response.status(201).entity("Paso el nft").build();
    }

    public void saveImage(InputStream uplInputStream, String filename, ServletContext context){
        int readBytes = 0;
        byte bytes[] = new byte[1024];

        try {
            String uploadPath = context.getRealPath("")+File.separator+UPLOAD_DIRECTORY+File.separator;

            File directory = new File(uploadPath);
            if(!directory.exists()){
                directory.mkdir();
            }

            OutputStream outputStream = new FileOutputStream(uploadPath + filename);
            while((readBytes = uplInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,readBytes);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
