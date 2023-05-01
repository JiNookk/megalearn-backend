package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.rating.CreateRatingService;
import jinookk.ourlms.applications.rating.GetRatingService;
import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetRatingService getRatingService;

    @MockBean
    private CreateRatingService createRatingService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void rate() throws Exception {
        RatingDto ratingDto = new RatingDto(5.0);

        given(createRatingService.rate(any(), any()))
                .willReturn(ratingDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/ratings")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"content\":\"this is hi\"," +
                                "\"courseId\":1," +
                                "\"rating\":5" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"rating\":5.0")
                ));
    }

    @Test
    void myRating() throws Exception {
        RatingDto ratingDto = new RatingDto(4.5);

        given(getRatingService.totalRating(new UserName("userName@email.com")))
                .willReturn(ratingDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/my-rating")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"rating\":4.5")
                ));
    }

    @Test
    void myReviews() throws Exception {
        RatingDto ratingDto = new RatingDto(4.5);

        given(getRatingService.myReviews(new UserName("userName@email.com")))
                .willReturn(new RatingsDto(List.of(ratingDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/ratings/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"ratings\":[")
                ));
    }

    @Test
    void list() throws Exception {
        RatingDto ratingDto = new RatingDto(4.5, new CourseId(1L), new Name("author", false), new Content("content"),
                LocalDateTime.now());

        given(getRatingService.list())
                .willReturn(new RatingsDto(List.of(ratingDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/ratings")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"ratings\":[")
                ));
    }
}
