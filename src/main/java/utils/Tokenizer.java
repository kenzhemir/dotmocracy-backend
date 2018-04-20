package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Tokenizer {
    private static Tokenizer instance = null;
    //HMAC
    private Algorithm algorithmHS;
    private JWTVerifier verifier;
    private String issuer;

    private Tokenizer() {
        issuer = "brogrammers";
        String secret = "somesecretphrasegoeshere";
        // Exists only to defeat instantiation.
        try {
            algorithmHS = Algorithm.HMAC256(secret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        verifier = JWT.require(algorithmHS)
                .withIssuer(issuer)
                .build();
    }

    public static Tokenizer getInstance() {
        if (instance == null) {
            instance = new Tokenizer();
        }
        return instance;
    }

    public static String extractTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.substring("Bearer".length()).trim();
    }

    public static String generateToken(String username, long user_id) {
        return Tokenizer.getInstance().generate(username, user_id);
    }

    public static String extractUsername(String token) {
        return Tokenizer.getInstance().decode(token).getClaim("user").asString();
    }
    public static Long extractID(String token) {
        return Tokenizer.getInstance().decode(token).getClaim("id").asLong();
    }


    private String generate(String username, long user_id) {
        return JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .withClaim("user", username)
                .withClaim("id", user_id)
                .sign(algorithmHS);
    }

    private DecodedJWT decode(String token) {
        System.out.println("[JWT] token: " + token);
        verifier.verify(token);
        DecodedJWT jwt = JWT.decode(token);
        System.out.println("[JWT] jwt: " + jwt);
        return jwt;
    }
}
