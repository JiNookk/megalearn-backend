package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LoginRequestDto;
import jinookk.ourlms.dtos.LoginResultDto;
import jinookk.ourlms.exceptions.InvalidPassword;
import jinookk.ourlms.exceptions.LoginFailed;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.services.LoginService;
import jinookk.ourlms.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    public SessionController(LoginService loginService, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        Name userName = new Name(loginRequestDto.getEmail());

        String password = loginRequestDto.getPassword();

        Account account = loginService.login(
                userName,
                password
        );

        String accessToken = jwtUtil.encode(userName);

        return new LoginResultDto(
                accessToken,
                account.name(),
                account.userName(),
                account.phoneNumber()
        );
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
}
