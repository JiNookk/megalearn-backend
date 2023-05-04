package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LoginRequestDto;
import jinookk.ourlms.dtos.LoginResultDto;
import jinookk.ourlms.exceptions.InvalidPassword;
import jinookk.ourlms.exceptions.LoginFailed;
import jinookk.ourlms.applications.auth.LoginService;
import jinookk.ourlms.applications.auth.IssueTokenService;
import jinookk.ourlms.utils.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("session")
public class SessionController {
    private final LoginService loginService;
    private final HttpUtil httpUtil;
    private final IssueTokenService issueTokenService;

    public SessionController(LoginService loginService,
                             HttpUtil httpUtil,
                             IssueTokenService issueTokenService) {
        this.loginService = loginService;
        this.httpUtil = httpUtil;
        this.issueTokenService = issueTokenService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(
            @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response
    ) {
        String refreshToken = issueTokenService.issueRefreshToken(loginRequestDto);

        ResponseCookie cookie = httpUtil.generateHttpOnlyCookie("refreshToken", refreshToken);

        httpUtil.addCookie(cookie, response);

        return loginService.login(loginRequestDto);
    }

    @ExceptionHandler(LoginFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Login failed";
    }

    @ExceptionHandler(InvalidPassword.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidPassword() {
        return "Invalid Password";
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }
}
