package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.UsersEntity;
import utils.UserHibernateUtil;
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
        String response = gson.toJson(UserHibernateUtil.readUsers());
        Response.ResponseBuilder builder = Response.status(200).entity(response);
        return builder.build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response onAuth(@HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        System.out.println("My log: onAuth");
        Gson gson = new Gson();
        ResponseBuilder responseBuilder;
        try {
            String token = Tokenizer.extractTokenFromHeader(authHeader);
            String login = Tokenizer.extractUsername(token);
            UsersEntity user = UserHibernateUtil.checkUser(login);
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
        UsersEntity user = UserHibernateUtil.checkUser(login);
        if (user != null && user.getPassword().equals(password)) {
            String token = Tokenizer.generateToken(user.getUsername(), user.getId());
            String data = gson.toJson(user);
            responseBuilder = Response.status(200)
                    .entity(data)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
//                    .cookie(new NewCookie("token", token, "/", uri.getBaseUri().getHost(), "comment", 3600*24, false));
        } else {
            responseBuilder = Response.status(401);
        }
        return responseBuilder.build();
    }

    @POST
    @Path("/logout")
    public Response onLogout(@HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = Tokenizer.extractTokenFromHeader(authHeader);
        System.out.println("My log: Logout");
        ResponseBuilder responseBuilder;
        if (token == null) {
            responseBuilder = Response.status(401);
        } else {
            responseBuilder = Response.status(200)
                    .header(HttpHeaders.AUTHORIZATION, "");
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
            if (UserHibernateUtil.checkUser(login) != null) {
                System.out.println("My log: check user failed");
                responseBuilder = Response.status(401);
            } else {
                System.out.println("My log: " + UserHibernateUtil.checkUser(login));
                System.out.println("My log: Create User");
                UsersEntity createdUser = UserHibernateUtil.createUser(login, password);
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

    @POST
    @Path("/change")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onDataChange(@HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader, String request) {
        System.out.println("My Log: onDataChange");
        System.out.println(authHeader);
        Gson gson = new Gson();
        ResponseBuilder responseBuilder;
        JsonParser parser = new JsonParser();
        if (request == null || request.isEmpty()) {
            return Response.status(400).build();
        }
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        String username = requestInfo.get("username").getAsString();
        String password = requestInfo.get("password").getAsString();
        if (authHeader == null || authHeader.isEmpty()) {
            System.out.println("auth problems");
            responseBuilder = Response.status(401);
        } else if (username == null || password == null) {
            String response = gson.toJson("wrong format");
            responseBuilder = Response.status(400).entity(response);
        } else {
            long user_id = Tokenizer.extractID(Tokenizer.extractTokenFromHeader(authHeader));
            UserHibernateUtil.updateUser(user_id, username, password);
            responseBuilder = Response.ok();
        }

        return responseBuilder.build();
    }
}
