package jinookk.ourlms.controllers;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jinookk.ourlms.exceptions.LoginFailed;
import jinookk.ourlms.exceptions.RefreshTokenExpired;
import jinookk.ourlms.applications.auth.IssueTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(TokenController.class)
class TokenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueTokenService issueTokenService;

    @Test
    void reissueAccessToken() throws Exception {
        String tokenValue = "header.payload.signature";

        given(issueTokenService.reissueAccessToken(any()))
                .willReturn(tokenValue);

        mockMvc.perform(MockMvcRequestBuilders.post("/accessToken")
                        .cookie(new Cookie("refreshToken", tokenValue))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(containsString(tokenValue)));
    }

    @Test
    void reissueAccessTokenWithInvalidRefreshToken() throws Exception {
        given(issueTokenService.reissueAccessToken(any()))
                .willThrow(JWTDecodeException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/accessToken")
                        .cookie(new Cookie("refreshToken", "thisIsInvalid"))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("invalid refreshToken")));
    }

    @Test
    void reissueAccessTokenWithExpiredRefreshToken() throws Exception {
        given(issueTokenService.reissueAccessToken(any()))
                .willThrow(RefreshTokenExpired.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/accessToken")
                        .cookie(new Cookie("refreshToken", "this.Is.Expired"))
                )
                .andExpect(MockMvcResultMatchers.status().is(498))
                .andExpect(MockMvcResultMatchers.content().string(containsString("refreshTokenExpired")));
    }

    @Test
    void reissueRefreshToken() throws Exception {
        String tokenValue = "header.payload.signature";

        given(issueTokenService.reissueRefreshToken(any(), any()))
                .willReturn(tokenValue);

        mockMvc.perform(MockMvcRequestBuilders.post("/refreshToken")
                .cookie(new Cookie("refreshToken", "this.Is.Expired"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"userName\":\"ojw0828\"," +
                                "\"password\":\"password\"" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(containsString("refreshToken issued")));
    }

    @Test
    void reissueRefreshTokenWithInvalidUserInformation() throws Exception {
        given(issueTokenService.reissueRefreshToken(any(), any()))
                .willThrow(LoginFailed.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/refreshToken")
                .cookie(new Cookie("refreshToken", "this.Is.Expired"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"userName\":\"ojw0828\"," +
                                "\"password\":\"invalidPassword\"" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("invalid UserInformation")));
    }
}
