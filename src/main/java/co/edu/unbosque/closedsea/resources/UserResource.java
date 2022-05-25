package co.edu.unbosque.closedsea.resources;

import co.edu.unbosque.closedsea.dto.User;
import co.edu.unbosque.closedsea.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static co.edu.unbosque.closedsea.services.UserService.*;

@Path("/users")
public class UserResource {

    @Context
    ServletContext context;

    //Credenciales BD
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/ClosedSea";
    static final String USER = "postgres";
    static final String PASS = "68218190";

    @GET
    @Produces("application/json")
    public Response listUsers() {
        Connection conn = null;
        List<User> users = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);

            //users = usersService.listUsers();
            users = usersService.getUsers();

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace(); //
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok().entity(users).build();
    }


   /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<User> users = getUsers();

            return Response.ok().entity(users).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        String contextPath = context.getRealPath("") + File.separator;
        try {
            user = createUser(user.getUsername(), user.getPassword(), user.getRole(), contextPath);
            return Response.created(UriBuilder.fromResource(UserResource.class).path(user.getUsername()).build()).entity(user).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public Response get(@PathParam("username") String username) {
        Connection conn = null;
        User user = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);
            List<User> users = getUsers();
             user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);

            conn.close();
        } catch (IOException e) {
            return Response.serverError().build();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.ok().entity(user).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createUser(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("role") String role

    ) {
        Connection conn = null;
        List<User> users = null;
        User user = null;

        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            UserService usersService = new UserService(conn);

            user = new User(username, password, role, "0");
            usersService.newUser(user);

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Response.created(UriBuilder.fromResource(UserResource.class).path(username).build())
                .entity(user)
                .build();
    }


}
