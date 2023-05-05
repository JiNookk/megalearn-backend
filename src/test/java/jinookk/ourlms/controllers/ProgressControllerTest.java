package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.progress.GetProgressService;
import jinookk.ourlms.applications.progress.UpdateProgressService;
import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Progress;
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

@WebMvcTest(ProgressController.class)
class ProgressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProgressService getProgressService;

    @MockBean
    private UpdateProgressService updateProgressService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));
    }

    @Test
    void progress() throws Exception {
        ProgressDto progressDto = Fixture.progress("테스트 1강").toDto();

        given(getProgressService.detail(any(), any())).willReturn(progressDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/1/progress")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"status\":\"processing\"")
                ));
    }

    @Test
    void list() throws Exception {
        ProgressDto progressDto = Fixture.progress("테스트 1강").toDto();

        ProgressesDto progressesDto = new ProgressesDto(List.of(progressDto));
        given(getProgressService.list(any(), any())).willReturn(progressesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/progresses")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"progresses\":[")
                ));
    }

    @Test
    void listByCourseId() throws Exception {
        ProgressDto progressDto = Fixture.progress("테스트 1강").toDto();

        ProgressesDto progressesDto = new ProgressesDto(List.of(progressDto));
        given(getProgressService.listByCourseId(any())).willReturn(progressesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/progresses"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"progresses\":[")
                ));
    }

    @Test
    void complete() throws Exception {
        Progress progress = Fixture.progress("테스트 1강");
        progress.complete();

        ProgressDto progressDto = progress.toDto();

        given(updateProgressService.complete(any())).willReturn(progressDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/progresses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"status\":\"completed\"")
                ));
    }

    @Test
    void updateTime() throws Exception {
        Progress progress = Fixture.progress("테스트 1강");

        ProgressDto progressDto = progress.toDto();

        given(updateProgressService.updateTime(any(), any())).willReturn(progressDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/progresses/1/time")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"minute\":1,\"second\":24}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectureTime\":{")
                ));
    }
}
