package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.MyCourseDto;
import jinookk.ourlms.dtos.MyCoursesDto;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.services.MyCourseService;
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

@WebMvcTest(MyCoursesController.class)
class MyCoursesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyCourseService myCourseService;

    @Test
    void myCourses() throws Exception {
        MyCourseDto myCourseDto = Course.fake("my-courses").toMyCourseDto();
        List<MyCourseDto> dtos = List.of(myCourseDto);

        given(myCourseService.list())
                .willReturn(new MyCoursesDto(dtos));

        mockMvc.perform(MockMvcRequestBuilders.get("/account/my-courses"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"myCourses\":[")
                ));
    }
}
