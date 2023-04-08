package jinookk.ourlms.services;

import jinookk.ourlms.models.entities.RefreshToken;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.repositories.RefreshTokenRepository;
import jinookk.ourlms.utils.HttpUtil;
import jinookk.ourlms.utils.JwtUtil;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TokenServiceTest {
    TokenService tokenService;
    JwtUtil jwtUtil;
    RefreshTokenRepository refreshTokenRepository;
    HttpUtil httpUtil;

    @BeforeEach
    void setup() {
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        httpUtil = new HttpUtil();
        jwtUtil = new JwtUtil("mySecret");
        tokenService = new TokenService(jwtUtil, refreshTokenRepository, httpUtil);
    }

    @Test
    void reissueAccessToken() {
        Name userName = new Name("ojw0828");

        String refreshToken = jwtUtil.encode(userName);

        String reIssuedToken = tokenService.reissueAccessToken(refreshToken);

        assertThat(jwtUtil.decode(reIssuedToken)).isEqualTo(userName);
    }

    @Test
    void reissueRefreshToken() {
        given(refreshTokenRepository.findByTokenValue(any()))
                .willReturn(Optional.of(RefreshToken.fake("expired")));

        String userName = "tester";

        String expiredToken = jwtUtil.generateRefreshToken(userName);

        HttpServletResponse response = mock(HttpServletResponse.class);

        String reIssuedToken = tokenService.reissueRefreshToken(expiredToken, response);

        assertThat(jwtUtil.decode(reIssuedToken)).isEqualTo(new Name(userName));
    }
}
