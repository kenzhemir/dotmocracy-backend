package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.UserEntity;
import sun.tools.jstat.Token;
import utils.HibernateUtil;
import utils.Tokenizer;
import utils.filter.JWTTokenNeeded;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */
@Path("/boards")
public class BoardService {
    @GET
    @Path("/")
    @JWTTokenNeeded
    public Response getUsers(@CookieParam("token") String token) {
        String username = Tokenizer.extractUsername(token);

        ResponseBuilder builder = Response.status(200);
        return builder.build();
    }
//
//    @GET
//    @Path("/")
//    @Produces(MediaType.APPLICATION_JSON)
//    @JWTTokenNeeded
//    public Response onAuth(@CookieParam("token") String token) {
//        String login = Tokenizer.extractUsername(token);
//        System.out.println("My log: onAuth");
//        Gson gson = new Gson();
//        ResponseBuilder responseBuilder;
//        try {
//            UserEntity user = HibernateUtil.checkUser(login);
//            if (user == null) throw new Exception("User does not exist");
//            responseBuilder = Response
//                    .status(200)
//                    .entity(gson.toJson(user));
//        } catch (Exception exception){
//            responseBuilder = Response.status(401);
//        }
//        return responseBuilder.build();
//    }
//
//    @POST
//    @Path("/login")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response onLogin(String request) {
//        System.out.println("My log: onLogin");
//        Gson gson = new Gson();
//        ResponseBuilder responseBuilder;
//        JsonParser parser = new JsonParser();
//        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
//        String login = requestInfo.get("username").getAsString();
//        String password = requestInfo.get("password").getAsString();
//        UserEntity user = HibernateUtil.checkUser(login);
//        if (user != null && user.getPassword().equals(password)) {
//            String token = Tokenizer.generateToken(user.getUsername());
//            String data = gson.toJson(user);
//            responseBuilder = Response.status(200)
//                    .entity(data)
//                    .cookie(new NewCookie("token", token));
//        } else {
//            responseBuilder = Response.status(401);
//        }
//        return responseBuilder.build();
//    }

}
