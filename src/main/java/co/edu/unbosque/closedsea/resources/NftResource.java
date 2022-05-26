package co.edu.unbosque.closedsea.resources;

import co.edu.unbosque.closedsea.dto.Nft;
import co.edu.unbosque.closedsea.dto.User;
import co.edu.unbosque.closedsea.services.NftService;
import co.edu.unbosque.closedsea.services.UserService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.apache.james.mime4j.dom.Multipart;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import static co.edu.unbosque.closedsea.services.UserService.getUsers;

@Path("/users/{username}/collections/{collection}/nfts")
public class NftResource {

    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "uploads";

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/ClosedSea";
    static final String USER = "postgres";
    static final String PASS = "68218190";

    @GET
    @Produces("application/json")
    public Response listNfts()
    {
        Connection conn = null;
        List<User> users = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            usersService.listUsers();

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace(); //
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok().entity(users).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadImage (MultipartFormDataInput input){
        Connection conn = null;
        try{
            String author = input.getFormDataPart("author", String.class, null);
            String collection = input.getFormDataPart("collection", String.class, null);
            String title = input.getFormDataPart("title", String.class, null);
            String price = input.getFormDataPart("price", String.class, null);

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            UserService userService= new UserService(conn);
            User user = userService.getUser(author);
            String authorReal = user.getUsername();

            NftService nftService = new NftService(conn);

            Map<String, List<InputPart>> formParts = input.getFormDataMap();
            List<InputPart> inputParts = formParts.get("customfile");

            for(InputPart inputPart : inputParts){
                try{
                    MultivaluedMap<String, String> header = inputPart.getHeaders();
                    String filename = "";
                    InputStream inputStream = inputPart.getBody(InputStream.class,null);

                    saveImage(inputStream, filename, context);
                    conn.close();
                } catch (IOException e){
                    return Response.serverError().build();
                }
            }
        } catch (IOException e){
            return Response.serverError().build();
        } catch (SQLException se){
            throw new RuntimeException(se);
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        return Response.status(201).entity("uploaded").build();
    }

   /* @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)

    public Response uploadPiece(MultipartFormDataInput input) {
        NftService nftService = new NftService();

        try {
            String username = input.getFormDataPart("author", String.class, null); //Cambiar despues por email
            //Agregar cuando este el sistema de colleciones
            String title = input.getFormDataPart("title", String.class, null);
            String fcoins = input.getFormDataPart("fcoins", String.class, null);

            List<User> userList = getUsers();
            User foundUser = userList.stream().filter(user -> username.equals(user.getUsername())).findFirst().orElse(null);
            Map<String, List<InputPart>> formPart = input.getFormDataMap();
            List<InputPart> inputParts = formPart.get("file");

            for (InputPart inputPart : inputParts) {
                try {
                    String indetifier = new NftService().generateRandomString(8);
                    String fileName = indetifier + "|" + title;

                    InputStream inputStream = inputPart.getBody(InputStream.class, null);
                    saveImage(inputStream, fileName, context);

                    NftService.createPiece(title, username, fcoins, fileName, context.getRealPath("") + File.separator);
                } catch (IOException e) {
                    return Response.serverError().build();
                }
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
        return Response.status(201).entity("Paso el nft").build();
    }*/

    public void saveImage(InputStream uplInputStream, String filename, ServletContext context) {
        int readBytes = 0;
        byte bytes[] = new byte[1024];

        try {
            String uploadPath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator;

            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdir();
            }

            OutputStream outputStream = new FileOutputStream(uploadPath + filename);
            while ((readBytes = uplInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, readBytes);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
