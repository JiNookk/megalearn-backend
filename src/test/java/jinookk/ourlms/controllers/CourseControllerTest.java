package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.dtos.MyCourseDto;
import jinookk.ourlms.dtos.MyCoursesDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.CourseService;
import jinookk.ourlms.services.MyCourseService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private MyCourseService myCourseService;

    @Test
    void create() throws Exception {
        CourseDto courseDto = Course.fake("courseTitle").toCourseDto();

        given(courseService.create(any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .header("Authorization", "Bearer ACCESS.TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"courseTitle\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"courseTitle\"")
                ));
    }

    @Test
    void detail() throws Exception {
        CourseDto courseDto = Course.fake("test").toCourseDto();

        given(courseService.detail(new AccountId(1L), new CourseId(1L))).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1")
                        .header("Authorization", "Bearer ACCESS.TOKEN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"test\"")
                ));
    }

    @Test
    void list() throws Exception {
        CourseDto courseDto = Course.fake("test").toCourseDto();

        given(courseService.list(anyInt(), any())).willReturn(new CoursesDto(List.of(courseDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"courses\":[")
                ));
    }

    @Test
    void purchasedMyCourses() throws Exception {
        CourseDto myCourseDto = Course.fake("my-courses").toCourseDto();
        List<CourseDto> dtos = List.of(myCourseDto);

        given(myCourseService.myCourses(any()))
                .willReturn(new CoursesDto(dtos));

        mockMvc.perform(MockMvcRequestBuilders.get("/account/my-courses")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"myCourses\":[")
                ));
    }

    @Test
    void update() throws Exception {
        CourseDto courseDto = Course.fake("updated").toCourseDto();

        given(courseService.update(any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/courses/1")
                        .header("Authorization", "Bearer ACCESS.TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"description\":\"description\",\n" +
                                "\"imagePath\":\"path\",\n" +
                                "\"status\":\"created\",\n" +
                                "\"price\":10000," +
                                "\"title\":\"updated\"," +
                                "\"level\":\"초급\"," +
                                "\"skill\":\"JS\"," +
                                "\"category\":\"category\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"updated\"")
                ));
    }

    @Test
    void delete() throws Exception {
        CourseDto courseDto = Course.fake(null).toCourseDto();

        given(courseService.delete(any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":null")
                ));
    }

    @Test
    void deleteSkill() throws Exception {
        CourseDto courseDto = Course.fake(null).toCourseDto();

        given(courseService.deleteSkill(any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1/skills/skill"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":null")
                ));
    }

    @Test
    void uploadedMyCourses() throws Exception {
        CourseDto courseDto = Course.fake("my-courses").toCourseDto();
        List<CourseDto> dtos = List.of(courseDto);

        given(myCourseService.uploadedList(any(), any()))
                .willReturn(new CoursesDto(dtos));

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/my-courses")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"courses\":[")
                ));
    }
}
