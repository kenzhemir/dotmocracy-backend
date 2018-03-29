package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.UserEntity;
import utils.HibernateUtil;
import utils.Tokenizer;
import utils.filter.JWTTokenNeeded;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */
@Path("/user")
public class UserService {
    @Context
    UriInfo uri;

    @GET
    @Path("/all")
    public Response getUsers() {
        System.out.println("My log: onAll");
        Gson gson = new Gson();
        String response = gson.toJson(HibernateUtil.readUsers());
        Response.ResponseBuilder builder = Response.status(200).entity(response);
        return builder.build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response onAuth(@CookieParam("token") String token) {
        String login = Tokenizer.extractUsername(token);
        System.out.println("My log: onAuth");
        Gson gson = new Gson();
        ResponseBuilder responseBuilder;
        try {
            UserEntity user = HibernateUtil.checkUser(login);
            if (user == null) throw new Exception("User does not exist");
            responseBuilder = Response
                    .status(200)
                    .entity(gson.toJson(user));
        } catch (Exception exception){
            responseBuilder = Response.status(401);
        }
        return responseBuilder.build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response onLogin(String request) {
        System.out.println("My log: onLogin");
        Gson gson = new Gson();
        ResponseBuilder responseBuilder;
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        String login = requestInfo.get("username").getAsString();
        String password = requestInfo.get("password").getAsString();
        UserEntity user = HibernateUtil.checkUser(login);
        if (user != null && user.getPassword().equals(password)) {
            String token = Tokenizer.generateToken(user.getUsername(), user.getId());
            String data = gson.toJson(user);
            responseBuilder = Response.status(200)
                    .entity(data)
                    .cookie(new NewCookie("token", token, "/", uri.getBaseUri().getHost(), "comment", 3600*24, false));
        } else {
            responseBuilder = Response.status(401);
        }
        return responseBuilder.build();
    }

    @POST
    @Path("/logout")
    public Response onLogout(@CookieParam("token") Cookie userCookie) {
        System.out.println("My log: Logout");
        ResponseBuilder responseBuilder;
        if (userCookie == null) {
            responseBuilder = Response.status(401);
        } else {
            responseBuilder = Response.status(200)
                    .cookie(new NewCookie(userCookie, "delete-cookie", 0, false));
        }
        return responseBuilder.build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onRegister(String request) {
        System.out.println("My log: Register");
        Gson gson = new Gson();
        ResponseBuilder responseBuilder;
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        if (requestInfo.get("username") == null || requestInfo.get("password") == null) {
            String response = gson.toJson("wrong format");
            responseBuilder = Response.status(401).entity(response);
        } else {
            String login = requestInfo.get("username").getAsString();
            String password = requestInfo.get("password").getAsString();
            if (HibernateUtil.checkUser(login) != null) {
                System.out.println("My log: check user failed");
                responseBuilder = Response.status(401);
            } else {
                System.out.println("My log: " + HibernateUtil.checkUser(login));
                System.out.println("My log: Create User");
                UserEntity createdUser = HibernateUtil.createUser(login, password);
                if (createdUser != null) {
                    String response = gson.toJson(createdUser);
                    responseBuilder = Response.status(200).entity(response);
                } else {
                    System.out.println("My log: Created user is null");
                    responseBuilder = Response.status(500);
                }

            }
        }
        return responseBuilder.build();
    }
}
