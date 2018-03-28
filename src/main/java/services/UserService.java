package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.UserEntity;
import utils.HibernateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */
@Path("/user")
public class UserService {

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
    public Response onAuth(@CookieParam("user") String userCookie) {
        System.out.println("My log: onAuth");
        Gson gson = new Gson();
        ResponseBuilder responseBuilder;
        if (userCookie == null) {
            responseBuilder = Response.status(401);
        } else {
            String response = gson.toJson(HibernateUtil.checkUser(userCookie));
            responseBuilder = Response.status(200).entity(response);
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
        String login = requestInfo.get("login").getAsString();
        String password = requestInfo.get("password").getAsString();
        UserEntity user = HibernateUtil.checkUser(login);
        if (user != null && user.getPassword().equals(password)) {
            String response = gson.toJson(user);
            responseBuilder = Response.status(200)
                    .entity(response)
                    .cookie(new NewCookie("user", user.getLogin()));
        } else {
            responseBuilder = Response.status(401);
        }
        return responseBuilder.build();
    }

    @POST
    @Path("/logout")
    public Response onLogout(@CookieParam("user") Cookie userCookie) {
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
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUser(String request) {
        System.out.println("My log: Check user");
        Gson gson = new Gson();
        ResponseBuilder responseBuilder;
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        String login = requestInfo.get("login").getAsString();
        UserEntity user = HibernateUtil.checkUser(login);
        String response = "";
        if (user == null) {
            response = gson.toJson("user does not exist");
        } else {
            response = gson.toJson(user);
        }
        responseBuilder = Response.status(200).entity(response);
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
        if (requestInfo.get("login") == null || requestInfo.get("password") == null) {
            String response = gson.toJson("wrong format");
            responseBuilder = Response.status(401).entity(response);
        } else {
            String login = requestInfo.get("login").getAsString();
            String password = requestInfo.get("password").getAsString();
            if (HibernateUtil.checkUser(login) != null) {
                System.out.println("My log: check user failed");
                responseBuilder = Response.status(401);
            } else {
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
