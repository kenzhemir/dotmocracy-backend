package services;

import utils.HibernateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.List;
import com.google.gson.Gson;

@Path("/test")
public class TestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        ResponseBuilder b;
        // Create a Gson object
        Gson gson = new Gson();
        // Read all
        List list = HibernateUtil.readUsers();
        // Create JSON string
        String json = gson.toJson(list);
        // Create a Response object
        b = Response.status(200).entity(json);
        // Return
        return b.build();
    }

}
