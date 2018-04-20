package services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.BoardsEntity;
import models.IdeasEntity;
import models.LogsEntity;
import models.UsersEntity;
import utils.*;
import utils.filter.JWTTokenNeeded;

import javax.jws.soap.SOAPBinding;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.List;

/**
 * Created by Assylkhanov Aslan on 02.03.2018.03.2018=
 */
@Path("/logs")
public class LogsService {
    @GET
    @Path("/")
    @JWTTokenNeeded
    public Response getLogs(@HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = Tokenizer.extractTokenFromHeader(authHeader);
        long id = Tokenizer.extractID(token);
        ResponseBuilder builder;
        UsersEntity user = UserHibernateUtil.checkUser(Tokenizer.extractUsername(token));
        if (user != null && user.getIsAdmin() != 0){
            List<LogsEntity> logs = LogsHibernateUtil.getLogs();
            String response_data = (new Gson()).toJson(logs);
            builder = Response.status(200).entity(response_data);
        } else {
            builder = Response.status(401);
        }
        return builder.build();
    }

}
