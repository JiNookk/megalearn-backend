package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import jinookk.ourlms.dtos.LoginResultDto;
import jinookk.ourlms.dtos.RegisterRequestDto;
import jinookk.ourlms.exceptions.RegisterFailed;
import jinookk.ourlms.models.exceptions.InvalidPhoneNumberLength;
import jinookk.ourlms.services.KakaoService;
import jinookk.ourlms.services.LoginService;
import jinookk.ourlms.services.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AccountController {
    private final KakaoService kakaoService;
    private final LoginService loginService;
    private final RegisterService registerService;

    public AccountController(KakaoService kakaoService, LoginService loginService, RegisterService registerService) {
        this.kakaoService = kakaoService;
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register", notes = "register new account")
    @ApiResponse(code = 400, message = "register failed", examples = @Example({
            @ExampleProperty(mediaType = "application/json", value = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}")
    }))
    private String register(
            @RequestBody RegisterRequestDto registerRequestDto
    ) {
        registerService.register(registerRequestDto);

        return "OK";
    }

    @PostMapping("/auth/token")
    @ResponseStatus(HttpStatus.CREATED)
    private LoginResultDto kaKaoLogin(
            @RequestParam String code
    ) {
        String kakaoToken = kakaoService.getAccessToken(code);

        Map<String, Object> userInfo = kakaoService.getUser(kakaoToken);

        return loginService.kakaoLogin(userInfo);
    }

    @ExceptionHandler(RegisterFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String registerFailed(RegisterFailed exception){
        return exception.getMessage();
    }

    @ExceptionHandler(InvalidPhoneNumberLength.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidPhoneNumberLength(InvalidPhoneNumberLength exception){
        return exception.getMessage();
    }
}
