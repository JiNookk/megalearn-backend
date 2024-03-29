package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import jinookk.ourlms.dtos.LoginResultDto;
import jinookk.ourlms.dtos.RegisterRequestDto;
import jinookk.ourlms.exceptions.RegisterFailed;
import jinookk.ourlms.applications.kakao.KakaoAuthService;
import jinookk.ourlms.applications.auth.LoginService;
import jinookk.ourlms.applications.auth.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class AccountController {
    private final KakaoAuthService kakaoAuthService;
    private final LoginService loginService;
    private final RegisterService registerService;

    public AccountController(KakaoAuthService kakaoAuthService, LoginService loginService, RegisterService registerService) {
        this.kakaoAuthService = kakaoAuthService;
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
    ) throws IOException {
        String kakaoToken = kakaoAuthService.getAccessToken(code);

        Map<String, Object> userInfo = kakaoAuthService.getUser(kakaoToken);

        return loginService.kakaoLogin(userInfo);
    }

    @ExceptionHandler(RegisterFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String registerFailed(RegisterFailed exception){
        return exception.getMessage();
    }

//    @ExceptionHandler(InvalidPhoneNumberLength.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String invalidPhoneNumberLength(InvalidPhoneNumberLength exception){
//        return exception.getMessage();
//    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String kakaoReadyFailed(IOException exception) {
        return exception.getMessage();
    }
}
