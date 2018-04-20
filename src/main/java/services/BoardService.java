package services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.BoardsEntity;
import models.IdeasEntity;
import utils.BoardsHibernateUtil;
import utils.IdeasHibernateUtil;
import utils.Tokenizer;
import utils.filter.JWTTokenNeeded;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.List;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */
@Path("/boards")
public class BoardService {
    @GET
    @Path("/")
    @JWTTokenNeeded
    public Response getBoards(@HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        System.out.println("GetBoards");
        System.out.println("Token : " + authHeader);
        long id = Tokenizer.extractID(Tokenizer.extractTokenFromHeader(authHeader));
        System.out.println("Id" + id);
        List<BoardsEntity> boards = BoardsHibernateUtil.readUserBoards(id);
        String response_data = (new Gson()).toJson(boards);
        ResponseBuilder builder = Response.status(200).entity(response_data);
        return builder.build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response putBoard(String request, @HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        System.out.println("PutBoard");
        System.out.println(authHeader);
        long user_id = Tokenizer.extractID(Tokenizer.extractTokenFromHeader(authHeader));
        System.out.println("User ID: " + user_id);
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        String category = requestInfo.get("category").getAsString();
        String topic = requestInfo.get("name").getAsString();
        BoardsEntity board = BoardsHibernateUtil.addBoard(user_id, category, topic, null);
        JsonArray array = requestInfo.getAsJsonArray("ideas");
        if (array != null) {
            System.out.println("Ideas: " + array.toString());
            if (board != null && array.size() != 0) {
                System.out.println("Putting ideas");
                for (int i = 0; i < array.size(); i++) {
                    JsonObject object = array.get(i).getAsJsonObject();
                    String ideaName = object.get("name").getAsString();
                    String ideaDescription = object.get("description").getAsString();
                    IdeasHibernateUtil.createIdea(board.getId(), ideaName, ideaDescription);
                }
            }
        }
        String response_data = (new Gson()).toJson(board);
        ResponseBuilder builder = Response.status(200).entity(response_data);
        return builder.build();
    }

    @GET
    @Path("/{id}")
    @JWTTokenNeeded
    public Response getBoard(@PathParam("id") int boardId) {
        BoardsEntity board = BoardsHibernateUtil.getBoardInfo(boardId);
        List<IdeasEntity> ideas = IdeasHibernateUtil.getBoardIdeas(boardId);
        board.setIdeas(ideas);
        String response_data = (new Gson()).toJson(board);
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
