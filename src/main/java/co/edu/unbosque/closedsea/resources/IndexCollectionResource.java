package co.edu.unbosque.closedsea.resources;

import co.edu.unbosque.closedsea.dto.Collection;
import co.edu.unbosque.closedsea.services.CollectionService;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/collections")
public class IndexCollectionResource {

    @Context
    ServletContext context;

    //Credenciales BD
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/ClosedSea";
    static final String USER = "postgres";
    static final String PASS = "68218190";

    @GET
    @Produces("application/json")
    public Response listCollections() {

        Connection conn = null;
        List<Collection> collection = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            CollectionService collectionService = new CollectionService(conn);
            collection = collectionService.listCollections();

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
        return Response.ok().entity(collection).build();
    }


   /* @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastCollections() throws IOException {
        CollectionService collectionService = new CollectionService();
        List<Collection> collections = collectionService.getLastCollections();

        return Response.ok().entity(collections).build();
    }*/

}
