import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */

@Path("/login")
public class LoginService {

    @GET
    public Response getUsers() {
        System.out.println("GetUsers");

        Gson gson = new Gson();
        String response = gson.toJson(HibernateUtil.readAll());

        Response.ResponseBuilder builder = Response.ok().entity(response);
        return builder.build();
    }

    @POST
    @Consumes("application/json")
    public Response onLogin(String request) {
        System.out.println("Request");

        JsonParser parser = new JsonParser();
        JsonObject requestInfo = parser.parse(request).getAsJsonObject();
        String username = requestInfo.get("username").getAsString();
        String password = requestInfo.get("password").getAsString();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        int token = username.hashCode() + password.hashCode();
        HibernateUtil.create(307, "MyLogin", "qwerty");

        //JSONObject jsonObject = new JSONObject();
        //JSONObject responseJson = new JSONObject();
        //responseJson.put(JSON_TOKEN, token);
        //System.out.println(responseJson.toString());
        Response.ResponseBuilder responseBuilder = Response.ok();
        //responseBuilder.entity(responseJson.toString());
        return responseBuilder.build();
    }
}
