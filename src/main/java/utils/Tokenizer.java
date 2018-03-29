package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;

import javax.ws.rs.core.Cookie;
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
        if(instance == null) {
            instance = new Tokenizer();
        }
        return instance;
    }

    public static String generateToken(String username){
        return Tokenizer.getInstance().generate(username);
    }
    public static String extractUsername(String token) {
        return Tokenizer.getInstance().extract(token);
    }


    private String generate(String username){
        return JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .withClaim("user", username)
                .sign(algorithmHS);
    }

    private String extract(String token) {
        verifier.verify(token);
        String jwt = JWT.decode(token).getClaim("user").asString();
        System.out.println("[JWT] jwt: " + jwt);
        return jwt;
    }
}
