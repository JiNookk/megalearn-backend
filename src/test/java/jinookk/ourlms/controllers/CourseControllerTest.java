package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.course.CreateCourseService;
import jinookk.ourlms.applications.course.DeleteCourseService;
import jinookk.ourlms.applications.course.GetCourseService;
import jinookk.ourlms.applications.course.UpdateCourseService;
import jinookk.ourlms.dtos.GetCoursesDto;
import jinookk.ourlms.dtos.CourseDto;
import jinookk.ourlms.dtos.CoursesDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.applications.course.MyCourseService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCourseService getCourseService;

    @MockBean
    private CreateCourseService createCourseService;

    @MockBean
    private UpdateCourseService updateCourseService;

    @MockBean
    private DeleteCourseService deleteCourseService;

    @MockBean
    private MyCourseService myCourseService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void create() throws Exception {
        CourseDto courseDto = Fixture.course("courseTitle").toCourseDto();


        given(createCourseService.create(any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"courseTitle\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"title\":\"courseTitle\"")
                ));
    }

    @Test
    void detail() throws Exception {
        CourseDto courseDto = Fixture.course("test").toCourseDto();

        given(getCourseService.detail(any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"test\"")
                ));
    }

    @Test
    void list() throws Exception {
        CourseDto courseDto = Fixture.course("test").toCourseDto();

        given(getCourseService.list(anyInt(), any())).willReturn(new CoursesDto(List.of(courseDto), 1));

        mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"courses\":[")
                ));
    }

    @Test
    void wishList() throws Exception {
        CourseDto courseDto = Fixture.course("test").toCourseDto();

        given(getCourseService.wishList(any())).willReturn(new GetCoursesDto(List.of(courseDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/wishes")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"courses\":[")
                ));
    }

    @Test
    void purchasedMyCourses() throws Exception {
        CourseDto myCourseDto = Fixture.course("my-courses").toCourseDto();
        List<CourseDto> dtos = List.of(myCourseDto);

        given(myCourseService.myCourses(any()))
                .willReturn(new GetCoursesDto(dtos));

        mockMvc.perform(MockMvcRequestBuilders.get("/account/my-courses")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"courses\":[")
                ));
    }

    @Test
    void update() throws Exception {
        CourseDto courseDto = Fixture.course("updated").toCourseDto();

        given(updateCourseService.update(any(), any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/courses/1")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"description\":\"description\",\n" +
                                "\"imagePath\":\"path\",\n" +
                                "\"status\":\"created\",\n" +
                                "\"price\":10000," +
                                "\"title\":\"updated\"," +
                                "\"level\":\"초급\"," +
                                "\"skills\":[\"JS\"]," +
                                "\"category\":\"category\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"updated\"")
                ));
    }

    @Test
    void updateStatus() throws Exception {
        CourseDto courseDto = Fixture.course("updated").toCourseDto();

        given(updateCourseService.updateStatus(any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/courses/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"status\":\"processing\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"updated\"")
                ));
    }

    @Test
    void delete() throws Exception {
        CourseDto courseDto = Fixture.course(null).toCourseDto();

        given(deleteCourseService.delete(any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1")
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":null")
                ));
    }

    @Test
    void deleteSkill() throws Exception {
        CourseDto courseDto = Fixture.course(null).toCourseDto();

        given(deleteCourseService.deleteSkill(any(), any(), any())).willReturn(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1/skills/skill")
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":null")
                ));
    }

    @Test
    void uploadedMyCourses() throws Exception {
        CourseDto courseDto = Fixture.course("my-courses").toCourseDto();
        List<CourseDto> dtos = List.of(courseDto);

        given(myCourseService.uploadedList(any(), any()))
                .willReturn(new GetCoursesDto(dtos));

        mockMvc.perform(MockMvcRequestBuilders.get("/instructor/my-courses")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"courses\":[")
                ));
    }
}
