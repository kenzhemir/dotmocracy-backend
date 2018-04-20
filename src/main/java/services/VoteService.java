package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.VotesEntity;
import utils.Tokenizer;
import utils.VotesHibernateUtil;
import utils.filter.JWTTokenNeeded;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */
@Path("/votes")
public class VoteService {
    @POST
    @Path("/add")
    @JWTTokenNeeded
    public Response addVote(String request, @HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        long id = Tokenizer.extractID(Tokenizer.extractTokenFromHeader(authHeader));
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        long option_id = requestInfo.get("option_id").getAsLong();
        int value = requestInfo.get("value").getAsInt();
        VotesEntity vote = VotesHibernateUtil.saveVote(option_id, id, value);
        String response_data = (new Gson()).toJson(vote);
        ResponseBuilder builder = Response.status(200).entity(response_data);
        return builder.build();
    }

    @POST
    @Path("/edit")
    @JWTTokenNeeded
    public Response editVote(String request, @HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        long id = Tokenizer.extractID(Tokenizer.extractTokenFromHeader(authHeader));
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        long vote_id = requestInfo.get("vote_id").getAsLong();
        int value = requestInfo.get("value").getAsInt();
        VotesEntity vote = VotesHibernateUtil.editVote(vote_id, id, value);
        String response_data = (new Gson()).toJson(vote);
        ResponseBuilder builder = Response.status(200).entity(response_data);
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
//            UsersEntity user = UserHibernateUtil.checkUser(login);
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
//        UsersEntity user = UserHibernateUtil.checkUser(login);
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
