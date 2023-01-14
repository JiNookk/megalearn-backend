package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LikeDto;
import jinookk.ourlms.dtos.LikesDto;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.ids.LikeId;
import jinookk.ourlms.services.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
    private LikeService likeService;

    @Test
    void list() throws Exception {
        LikeDto likeDto = Like.fake(true).toDto();

        given(likeService.list()).willReturn(new LikesDto(List.of(likeDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/likes"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"likes\":[")
                ));
    }

    @Test
    void like() throws Exception {
        LikeDto likeDto = Like.fake(true).toDto();

        given(likeService.detail(any(), any())).willReturn(likeDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/likes/me")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"clicked\":true")
                ));
    }

    @Test
    void toggle() throws Exception {
        LikeDto likeDto = Like.fake(false).toDto();

        given(likeService.toggle(any())).willReturn(likeDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/likes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"clicked\":false")
                ));
    }
}
