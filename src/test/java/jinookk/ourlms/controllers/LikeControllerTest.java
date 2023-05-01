package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.like.GetLikeService;
import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.dtos.LikesDto;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.applications.like.UpdateLikeService;
import jinookk.ourlms.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetLikeService getLikeService;

    @MockBean
    private UpdateLikeService updateLikeService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void list() throws Exception {
        LikeDto likeDto = Like.fake(true).toDto();

        given(getLikeService.list()).willReturn(new LikesDto(List.of(likeDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/likes"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"likes\":[")
                ));
    }

    @Test
    void like() throws Exception {
        LikeDto likeDto = Like.fake(true).toDto();

        given(getLikeService.detail(any(), any())).willReturn(likeDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/likes/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"clicked\":true")
                ));
    }

    @Test
    void toggle() throws Exception {
        LikeDto likeDto = Like.fake(false).toDto();

        given(updateLikeService.toggle(any())).willReturn(likeDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/likes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"clicked\":false")
                ));
    }
}
