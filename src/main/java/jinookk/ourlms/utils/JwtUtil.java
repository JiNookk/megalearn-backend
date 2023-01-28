package jinookk.ourlms.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jinookk.ourlms.models.vos.Name;

public class JwtUtil {
    private final Algorithm algorithm;

    public JwtUtil(String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String encode(Name userName) {
        return JWT.create()
                .withClaim("userName", userName.value())
                .sign(algorithm);
    }

    public Name decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT verify = verifier.verify(token);
        String value = verify.getClaim("userName").asString();
        return new Name(value);
    }
}
