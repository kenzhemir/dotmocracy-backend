package services;

import models.UserEntity;
import utils.HibernateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.*;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */
@Path("/user")
public class UserService {

    @GET
    @Path("/all")
    public Response getUsers() {
        Gson gson = new Gson();
        String response = gson.toJson(HibernateUtil.readUsers());
        Response.ResponseBuilder builder = Response.status(200).entity(response);
        return builder.build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response onAuth(@CookieParam("user") String userCookie) {
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
        Gson gson = new Gson();
        ResponseBuilder responseBuilder;
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        String login = requestInfo.get("login").getAsString();
        String password = requestInfo.get("password").getAsString();
        if (HibernateUtil.checkUser(login) != null) {
            responseBuilder = Response.status(401);
        } else {
            String response = gson.toJson(HibernateUtil.createUser(login, password));
            responseBuilder = Response.status(200).entity(response);
        }
        return responseBuilder.build();
    }
}
