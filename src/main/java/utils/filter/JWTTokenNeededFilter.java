package utils.filter;

import models.UsersEntity;
import utils.HibernateUtil;
import utils.Tokenizer;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Based on Antonio Goncalves's code
 * Edited by Miras Kenzhegaliyev
 * http://www.antoniogoncalves.org
 * --
 */
@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    // ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

//        // Get the HTTP Authorization header from the request
//        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//        logger.info("#### authorizationHeader : " + authorizationHeader);
//
//        // Check if the HTTP Authorization header is present and formatted correctly
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            logger.severe("#### invalid authorizationHeader : " + authorizationHeader);
//            throw new NotAuthorizedException("Authorization header must be provided");
//        }
//
//        // Extract the token from the HTTP Authorization header
//        String token = authorizationHeader.substring("Bearer".length()).trim();
//
//        try {
//
//            // Validate the token
//            Key key = keyGenerator.generateKey();
//            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
//            logger.info("#### valid token : " + token);
//
//        } catch (Exception e) {
//            logger.severe("#### invalid token : " + token);
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//        }

        if (!requestContext.getCookies().containsKey("token")) {
            System.out.println("[JWT] no token detected. Cookie keys: "+requestContext.getCookies().keySet());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        String token = requestContext.getCookies().get("token").getValue();
        System.out.println("[JWT] token: " + token);
        try {
            String username = Tokenizer.extractUsername(token);

            System.out.println("[JWT] token: " + token);
            UsersEntity user = HibernateUtil.checkUser(username);
            if (user == null) throw new Exception("User is null");
        } catch (Exception e) {
            System.out.println("[JWT] Authorization failed: ");
            System.out.println(e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

    }
}