package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.RatingDto;
import jinookk.ourlms.dtos.RatingsDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    void myRating() throws Exception {
        RatingDto ratingDto = new RatingDto(4.5);

        given(ratingService.totalRating(new AccountId(1L)))
                .willReturn(ratingDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/my-rating")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"rating\":4.5")
                ));
    }

    @Test
    void list() throws Exception {
        RatingDto ratingDto = new RatingDto(4.5, new CourseId(1L), new Name("author"), new Content("content"),
                LocalDateTime.now());

        given(ratingService.list())
                .willReturn(new RatingsDto(List.of(ratingDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/ratings")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"ratings\":[")
                ));
    }
}
