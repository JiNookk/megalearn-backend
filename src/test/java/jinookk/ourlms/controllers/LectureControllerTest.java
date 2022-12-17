package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.services.LectureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LectureController.class)
class LectureControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureService lectureService;

    @Test
    void lecture() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        given(lectureService.detail(1L))
                .willReturn(lectureDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/unit/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"test lecture 1\"")
                ));
    }

    @Test
    void list() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        LecturesDto lecturesDto = new LecturesDto(List.of(lectureDto));

        given(lectureService.list(1L))
                .willReturn(lecturesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/unit"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectures\":[")
                ));
    }
}
