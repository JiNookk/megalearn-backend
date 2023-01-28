package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LoginResultDto;
import jinookk.ourlms.dtos.RegisterRequestDto;
import jinookk.ourlms.services.KakaoService;
import jinookk.ourlms.services.LoginService;
import jinookk.ourlms.services.RegisterService;
import org.springframework.http.HttpStatus;
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
    private String register(
            @RequestBody RegisterRequestDto registerRequestDto
    ) {
        return registerService.register(registerRequestDto);
    }

    @PostMapping("/auth/token")
    @ResponseStatus(HttpStatus.CREATED)
    private LoginResultDto kaKaoLogin(
            @RequestParam String code
    ) {
        String accessToken = kakaoService.getAccessToken(code);

        Map<String, Object> userInfo = kakaoService.getUser(accessToken);

        return loginService.kakaoLogin(userInfo);
    }
}
