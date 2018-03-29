package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by Assylkhanov Aslan on 29.03.2018.03.2018=
 */
@Path("boards")
public class BoardsService {

    @GET
    @Path("/{id}")
    public Response getBoard(@PathParam("id") int boardId) {
        System.out.println("My Log: getBoards");
        Response.ResponseBuilder responseBuilder = Response.ok().entity("getBoards");

        return responseBuilder.build();
    }

}
