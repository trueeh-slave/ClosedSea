package co.edu.unbosque.closedsea.resources;

import co.edu.unbosque.closedsea.dto.Collection;
import co.edu.unbosque.closedsea.services.CollectionService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/users/{username}/collections")
public class CollectionResource {
    @Context
    ServletContext context;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/ClosedSea";
    static final String USER = "postgres";
    static final String PASS = "68218190";

    @GET
    @Produces("application/json")
    public Response getUsersList (@PathParam("username") String username){
        Connection conn = null;
        List<Collection> collections = null;

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            CollectionService collectionService = new CollectionService(conn);
            collections = collectionService.listCollections();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Response.ok().entity(collections).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createCollection(@PathParam("username") String username, @FormParam("collection") String collection, @FormParam("category") String category) {
        Connection conn = null;
        List<Collection> collections = null;
        Collection collectionobj = null;

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            CollectionService collectionService = new CollectionService(conn);

            collectionobj = new Collection(username,collection,category);
            collectionService.createCollection(collectionobj);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            try{
                if(conn != null) conn.close();
            } catch (SQLException se){
                se.printStackTrace();
            }
        }
        return Response.created(UriBuilder.fromResource(UserResource.class).path(username).build()).entity(collectionobj).build();
    }

    @GET
    @Path("/{collection}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollection(@PathParam("username") String username, @PathParam("collection") String collection){
        Connection conn = null;
        Collection collectionobj = null;

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            CollectionService collectionService = new CollectionService(conn);
            collectionobj = collectionService.getCollection(username,collection,"default");
            conn.close();
        } catch (SQLException se){
            se.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            try{
                if(conn != null){
                    conn.close();
                }
            } catch (SQLException se){
                se.printStackTrace();
            }
        }
        if(collectionobj != null){
            return Response.ok().entity(collectionobj).build();
        } else {
            return Response.status(404).entity("Collection not found").build();
        }
    }

}
