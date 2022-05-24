package co.edu.unbosque.closedsea.resources;

import co.edu.unbosque.closedsea.dto.Collection;
import co.edu.unbosque.closedsea.services.CollectionService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@Path("/users/{username}/colletions")
public class ArtistCollectionResource {
    @Context
    ServletContext context;

    Connection conn = null;

      @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollections(@PathParam("username") String username) throws IOException {
        CollectionService collectionService = new CollectionService(conn);
        List<Collection> collections = collectionService.getCollectionByUsername(username);

        return Response.ok().entity(collections).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response collections (@PathParam("username") String username, @FormParam("collection") String collection, @FormParam("size") String size){
        String contextPath = context.getRealPath("") + File.separator;

        try{
            Collection collection1 = new CollectionService(conn).createCollection(username,collection,"0",contextPath);
            return Response.ok().entity(collection1).build();
        } catch (IOException e){
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{collection}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollectionPath(@PathParam("username") String username, @PathParam("collection") String collection){
        try{
            CollectionService collectionService = new CollectionService(conn);
            List<Collection> collections = collectionService.getCollectionByUsername(username);

            Collection collection1 = collections.stream().filter(u -> u.getCollectionName().equals(collection)).findFirst().orElse(null);

            if(collection1 != null){
                return Response.ok().entity(collection1).build();
            } else {
                //Si no lo encuentra
                return Response.status(404).entity("user not found").build();
            }
        } catch (IOException e){
            return Response.serverError().build();
        }
    }
}
