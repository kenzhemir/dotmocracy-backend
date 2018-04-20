package services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.BoardsEntity;
import utils.BoardsHibernateUtil;
import utils.SuperMegaLogger;
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
        String token = Tokenizer.extractTokenFromHeader(authHeader);
        long id = Tokenizer.extractID(token);
        List boards = BoardsHibernateUtil.readUserBoards(id);
        String response_data = (new Gson()).toJson(boards);
        ResponseBuilder builder = Response.status(200).entity(response_data);
        return builder.build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response putBoard(String request, @HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader,  @HeaderParam("user-agent") String userAgent) {
        System.out.println("PutBoard");
        String token = Tokenizer.extractTokenFromHeader(authHeader);
        long user_id = Tokenizer.extractID(token);
        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        String category = requestInfo.get("category").getAsString();
        String topic = requestInfo.get("title").getAsString();
        JsonArray ideas = requestInfo.get("ideas").getAsJsonArray();

        BoardsEntity board = BoardsHibernateUtil.addBoard(user_id, category, topic, null);

        ResponseBuilder builder;
        System.out.println(board.getId());
        if (board == null){
            builder = Response.status(500);
        } else {
            String response_data = (new Gson()).toJson(board);
            builder = Response.status(200).entity(response_data);
            SuperMegaLogger.log(Tokenizer.extractUsername(token), "Added board: "+topic, userAgent);
        }
        return builder.build();
    }


}
