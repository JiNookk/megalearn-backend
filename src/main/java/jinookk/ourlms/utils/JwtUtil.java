package jinookk.ourlms.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jinookk.ourlms.exceptions.RefreshTokenExpired;
import jinookk.ourlms.exceptions.RefreshTokenInvalid;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;

import java.util.Calendar;
import java.util.Date;

public class JwtUtil {
    private final Algorithm algorithm;
    private static final int ACCESSTOKEN_EXPIRATION_TIME_MINUTES = 1500;
    private static final int REFRESHTOKEN_EXPIRATION_TIME_DATES = 14;

    public JwtUtil(String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String encode(UserName userName) {
        Date expirationDate = getExpirationDate(ACCESSTOKEN_EXPIRATION_TIME_MINUTES);
        return JWT.create()
                .withExpiresAt(expirationDate)
                .withClaim("userName", userName.value())
                .sign(algorithm);
    }

    public UserName decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT verify = verifier.verify(token);
        String value = verify.getClaim("userName").asString();
        return new UserName(value);
    }

    private Date getExpirationDate(int time) {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, time);

        return calendar.getTime();
    }

    public String generateRefreshToken(String userName) {
        Date expirationDate = getExpirationDate(REFRESHTOKEN_EXPIRATION_TIME_DATES * 24 * 60);

        return JWT.create()
                .withClaim("userName", userName)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String regenerateJWT(String refreshToken) {
        try {
            UserName userName = decode(refreshToken);

            return encode(userName);
        } catch (JWTDecodeException e) {
            throw new RefreshTokenInvalid(refreshToken);
        } catch (TokenExpiredException e){
            throw new RefreshTokenExpired(refreshToken);
        }
    }
}
