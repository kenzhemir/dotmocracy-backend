package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.IdeasEntity;
import utils.IdeasHibernateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by Assylkhanov Aslan on 29.03.2018.03.2018=
 */
@Path("/boards")
public class IdeasService {

    @GET
    @Path("/{id}/ideas")
    public Response getBoardIdeas(@PathParam("id") int boardId) {
        System.out.println("My Log. GetBoardIdeas getIdeas");
        Response.ResponseBuilder responseBuilder = Response.ok();
        List<IdeasEntity> ideasList = IdeasHibernateUtil.getBoardIdeas(boardId);
        Gson gson = new Gson();
        responseBuilder.entity(gson.toJson(ideasList));
        return responseBuilder.build();
    }

    @POST
    @Path("/{id}/ideas")
    @Consumes(APPLICATION_JSON)
    public Response createIdea(@PathParam("id") int boardId, String requestBody) {
        System.out.println("My log. CreateIdea");
        System.out.printf("My log. %s", requestBody);
        Response.ResponseBuilder responseBuilder;
        JsonObject jsonRequest = new JsonParser().parse(requestBody).getAsJsonObject();
        String name = jsonRequest.get("name").getAsString();
        if (IdeasHibernateUtil.createIdea(boardId, name)) {
            responseBuilder = Response.status(201);
        } else {
            responseBuilder = Response.status(500);
        }
        return responseBuilder.build();
    }

}