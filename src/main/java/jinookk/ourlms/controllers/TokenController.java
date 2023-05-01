package jinookk.ourlms.controllers;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jinookk.ourlms.exceptions.LoginFailed;
import jinookk.ourlms.exceptions.RefreshTokenExpired;
import jinookk.ourlms.applications.token.IssueTokenService;
import jinookk.ourlms.utils.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TokenController {
    private final IssueTokenService issueTokenService;
    private final HttpUtil httpUtil;

    public TokenController(IssueTokenService issueTokenService, HttpUtil httpUtil) {
        this.issueTokenService = issueTokenService;
        this.httpUtil = httpUtil;
    }

    @PostMapping("accessToken")
    @ResponseStatus(HttpStatus.CREATED)
    public String reissueAccessToken(HttpServletRequest request) {
        String refreshToken = httpUtil.getCookieValue(request, "refreshToken");

        return issueTokenService.reissueAccessToken(refreshToken);
    }

    @PostMapping("refreshToken")
    @ResponseStatus(HttpStatus.CREATED)
    public String reissueRefreshToken(HttpServletRequest request,
                                      HttpServletResponse response) {
        String refreshToken = httpUtil.getCookieValue(request, "refreshToken");

        issueTokenService.reissueRefreshToken(refreshToken, response);

        return "refreshToken issued";
    }

    @ExceptionHandler(RefreshTokenExpired.class)
    public String refreshTokenExpired(HttpServletResponse response) {
        response.setStatus(498);
        return "refreshTokenExpired";
    }

    @ExceptionHandler(JWTDecodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidRefreshToken() {
        return "invalid refreshToken";
    }

    @ExceptionHandler(LoginFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidUserInformation() {
        return "invalid UserInformation";
    }
}
