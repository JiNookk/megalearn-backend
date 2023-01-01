package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.entities.Lecture;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.LectureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
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
    void create() throws Exception {
        LectureDto lectureDto = Lecture.fake("hi").toLectureDto();

        given(lectureService.create(any()))
                .willReturn(lectureDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"hi\"," +
                                "\"courseId\":1," +
                                "\"sectionId\":1" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"hi\"")
                ));
    }

    @Test
    void lecture() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        given(lectureService.detail(1L))
                .willReturn(lectureDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/lectures/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"test lecture 1\"")
                ));
    }

    @Test
    void list() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        LecturesDto lecturesDto = new LecturesDto(List.of(lectureDto));

        given(lectureService.list(new CourseId(1L)))
                .willReturn(lecturesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/lectures"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectures\":[")
                ));
    }

    @Test
    void listByInstructorId() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        LecturesDto lecturesDto = new LecturesDto(List.of(lectureDto));

        given(lectureService.listByInstructorId(new AccountId(1L)))
                .willReturn(lecturesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/lectures")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectures\":[")
                ));
    }

    @Test
    void update() throws Exception {
        LectureDto lectureDto = Lecture.fake("updated").toLectureDto();

        given(lectureService.update(any(), any()))
                .willReturn(lectureDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/lectures/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"updated\"," +
                                "\"videoUrl\":\"url\"," +
                                "\"lectureNote\":\"note\"," +
                                "\"filePath\":\"path\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"updated\"")
                ));
    }

    @Test
    void delete() throws Exception {
        LectureDto lectureDto = Lecture.fake(null).toLectureDto();

        given(lectureService.delete(any()))
                .willReturn(lectureDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/lectures/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":null")
                ));
    }
}
