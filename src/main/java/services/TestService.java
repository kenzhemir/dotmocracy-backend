package services;

import com.google.gson.Gson;
import utils.UserHibernateUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.List;

@Path("/test")
public class TestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        System.out.println("My log: Test");
        ResponseBuilder b;
        // Create a Gson object
        Gson gson = new Gson();
        // Read all
        List list = UserHibernateUtil.readUsers();
        // Create JSON string
        String json = gson.toJson(list);
        // Create a Response object
        b = Response.status(200).entity(json);
        // Return
        return b.build();
    }

}
