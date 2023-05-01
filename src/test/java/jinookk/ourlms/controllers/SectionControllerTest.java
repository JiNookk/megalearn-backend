package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.section.CreateSectionService;
import jinookk.ourlms.applications.section.DeleteSectionService;
import jinookk.ourlms.applications.section.GetSectionService;
import jinookk.ourlms.applications.section.UpdateSectionService;
import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.dtos.SectionsDto;
import jinookk.ourlms.models.entities.Section;
import jinookk.ourlms.models.vos.UserName;
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

@WebMvcTest(SectionController.class)
class SectionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetSectionService getSectionService;

    @MockBean
    private CreateSectionService createSectionService;

    @MockBean
    private UpdateSectionService updateSectionService;

    @MockBean
    private DeleteSectionService deleteSectionService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void create() throws Exception {
        SectionDto sectionDto = Section.fake("hi").toSectionDto();

        given(createSectionService.create(any()))
                .willReturn(sectionDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"hi\"," +
                                "\"goal\":\"\"," +
                                "\"courseId\":1" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"hi\"")
                ));
    }

    @Test
    void list() throws Exception {
        SectionDto sectionDto = Section.fake("hi").toSectionDto();

        given(getSectionService.list())
                .willReturn(new SectionsDto(List.of(sectionDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/sections")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"sections\":[")
                ));
    }

//    @Test
//    void listByCourseId() throws Exception {
//        List<Progress> progresses = List.of(Progress.fake("hi"));
//
//        SectionWithProgressDto sectionWithProgressDto = Section.fake("hi").toSectionWithProgressDto(progresses);
//
//        given(getSectionService.listWithProgress(any(), any()))
//                .willReturn(new SectionsWithProgressDto(List.of(sectionWithProgressDto)));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/sections")
//                        .header("Authorization", "Bearer " + accessToken))
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        containsString("\"sections\":[")
//                ));
//    }

    @Test
    void update() throws Exception {
        SectionDto sectionDto = Section.fake("updated").toSectionDto();

        given(updateSectionService.update(any(), any()))
                .willReturn(sectionDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/sections/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"updated\"," +
                                "\"goal\":\"goal\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"updated\"")
                ));
    }

    @Test
    void delete() throws Exception {
        SectionDto sectionDto = Section.fake(null).toSectionDto();

        given(deleteSectionService.delete(any()))
                .willReturn(sectionDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/sections/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":null")
                ));
    }
}
