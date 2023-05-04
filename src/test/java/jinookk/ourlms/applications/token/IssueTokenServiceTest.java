package jinookk.ourlms.applications.token;

import jinookk.ourlms.applications.auth.IssueTokenService;
import jinookk.ourlms.models.entities.RefreshToken;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.repositories.RefreshTokenRepository;
import jinookk.ourlms.utils.HttpUtil;
import jinookk.ourlms.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IssueTokenServiceTest {
    IssueTokenService issueTokenService;
    JwtUtil jwtUtil;
    RefreshTokenRepository refreshTokenRepository;
    HttpUtil httpUtil;

    @BeforeEach
    void setup() {
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        httpUtil = new HttpUtil();
        jwtUtil = new JwtUtil("mySecret");
        issueTokenService = new IssueTokenService(jwtUtil, refreshTokenRepository, httpUtil);
    }

    @Test
    void reissueAccessToken() {
        UserName userName = new UserName("userName@email.com");

        String refreshToken = jwtUtil.encode(userName);

        String reIssuedToken = issueTokenService.reissueAccessToken(refreshToken);

        assertThat(jwtUtil.decode(reIssuedToken)).isEqualTo(userName);
    }

    @Test
    void reissueRefreshToken() {
        given(refreshTokenRepository.findByTokenValue(any()))
                .willReturn(Optional.of(RefreshToken.fake("expired")));

        String userName = "tester@email.com";

        String expiredToken = jwtUtil.generateRefreshToken(userName);

        HttpServletResponse response = mock(HttpServletResponse.class);

        String reIssuedToken = issueTokenService.reissueRefreshToken(expiredToken, response);

        assertThat(jwtUtil.decode(reIssuedToken)).isEqualTo(new UserName(userName));
    }
}
