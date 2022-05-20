package co.edu.unbosque.closedsea.resources;

import co.edu.unbosque.closedsea.dto.Collection;
import co.edu.unbosque.closedsea.services.CollectionService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

@Path("/collections")
public class IndexCollectionResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastCollections() throws IOException {
        CollectionService collectionService = new CollectionService();
        List<Collection> collections = collectionService.getLastCollections();

        return Response.ok().entity(collections).build();
    }

}
