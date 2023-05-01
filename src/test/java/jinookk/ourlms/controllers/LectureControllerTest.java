package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.lecture.CreateLectureService;
import jinookk.ourlms.applications.lecture.DeleteLectureService;
import jinookk.ourlms.applications.lecture.GetLectureService;
import jinookk.ourlms.applications.lecture.UpdateLectureService;
import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LecturesDto;
import jinookk.ourlms.models.entities.Lecture;
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
    private GetLectureService getLectureService;

    @MockBean
    private CreateLectureService createLectureService;

    @MockBean
    private UpdateLectureService updateLectureService;

    @MockBean
    private DeleteLectureService deleteLectureService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void create() throws Exception {
        LectureDto lectureDto = Lecture.fake("hi").toLectureDto();

        given(createLectureService.create(any()))
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

        given(getLectureService.detail(1L))
                .willReturn(lectureDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/lectures/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"test lecture 1\"")
                ));
    }

    @Test
    void listByCourseId() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        LecturesDto lecturesDto = new LecturesDto(List.of(lectureDto));

        given(getLectureService.listByCourseId(new CourseId(1L)))
                .willReturn(lecturesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/lectures"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectures\":[")
                ));
    }

    @Test
    void list() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        LecturesDto lecturesDto = new LecturesDto(List.of(lectureDto));

        given(getLectureService.list())
                .willReturn(lecturesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/lectures"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectures\":[")
                ));
    }

    @Test
    void listByInstructorId() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        LecturesDto lecturesDto = new LecturesDto(List.of(lectureDto));

        given(getLectureService.listByInstructorId(new UserName("userName@email.com")))
                .willReturn(lecturesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/instructor")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectures\":[")
                ));
    }

    @Test
    void myLectures() throws Exception {
        LectureDto lectureDto = Lecture.fake("test lecture 1").toLectureDto();

        LecturesDto lecturesDto = new LecturesDto(List.of(lectureDto));

        given(getLectureService.myLectures(new UserName("userName@email.com")))
                .willReturn(lecturesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectures\":[")
                ));
    }

    @Test
    void update() throws Exception {
        LectureDto lectureDto = Lecture.fake("updated").toLectureDto();

        given(updateLectureService.update(any(), any()))
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
        LectureDto lectureDto = Lecture.fake((String) null).toLectureDto();

        given(deleteLectureService.delete(any()))
                .willReturn(lectureDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/lectures/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":null")
                ));
    }
}
