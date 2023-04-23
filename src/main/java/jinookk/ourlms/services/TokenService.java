package jinookk.ourlms.services;

import jinookk.ourlms.dtos.LoginRequestDto;
import jinookk.ourlms.exceptions.RefreshTokenNotFound;
import jinookk.ourlms.models.entities.RefreshToken;
import jinookk.ourlms.repositories.RefreshTokenRepository;
import jinookk.ourlms.utils.HttpUtil;
import jinookk.ourlms.utils.JwtUtil;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@Transactional
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final HttpUtil httpUtil;

    public TokenService(JwtUtil jwtUtil,
                        RefreshTokenRepository refreshTokenRepository,
                        HttpUtil httpUtil) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.httpUtil = httpUtil;
    }

    public String issueRefreshToken(LoginRequestDto loginRequestDto) {
        String tokenValue = jwtUtil.generateRefreshToken(loginRequestDto.getEmail());
        
        RefreshToken refreshToken = RefreshToken.of(loginRequestDto, tokenValue);

        RefreshToken saved = refreshTokenRepository.save(refreshToken);

        return saved.tokenValue();
    }
    public String reissueAccessToken(String refreshToken) {
        return jwtUtil.regenerateJWT(refreshToken);
    }

    public String reissueRefreshToken(String expiredToken,
                                      HttpServletResponse response) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenValue(expiredToken)
                .orElseThrow(() -> new RefreshTokenNotFound(expiredToken));

        String userName = refreshToken.userName();

        String reissued = jwtUtil.generateRefreshToken(userName);

        refreshToken.updateTokenValue(reissued);

        ResponseCookie cookie = httpUtil.generateHttpOnlyCookie("refreshToken", reissued);
        httpUtil.addCookie(cookie, response);

        return reissued;
    }
}
