package jinookk.ourlms.advices;

import jinookk.ourlms.exceptions.AccessTokenExpired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TokenExpiryAdvice {
    @ResponseBody
    @ExceptionHandler(AccessTokenExpired.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String accessTokenExpired() {
        return "accessToken expired";
    }
}
