package services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.BoardsEntity;
import models.IdeasEntity;
import utils.BoardsHibernateUtil;
import utils.SuperMegaLogger;
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
    public Response putBoard(String request, @HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader,
            @HeaderParam("user-agent") String userAgent) {
        System.out.println("PutBoard");
        System.out.println(authHeader);
        String token = Tokenizer.extractTokenFromHeader(authHeader);
        long user_id = Tokenizer.extractID(token);
        System.out.println("User ID: " + user_id);
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        String category = requestInfo.get("category").getAsString();
        String topic = requestInfo.get("title").getAsString();
        JsonArray ideas = requestInfo.get("ideas").getAsJsonArray();

        ResponseBuilder builder;
        BoardsEntity board = BoardsHibernateUtil.addBoard(user_id, category, topic, null);
        if (board != null) {
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
            builder = Response.status(200).entity(response_data);
            SuperMegaLogger.log(Tokenizer.extractUsername(token), "Added board: " + topic, userAgent);
        } else {
            builder = Response.status(500);
        }
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
}
