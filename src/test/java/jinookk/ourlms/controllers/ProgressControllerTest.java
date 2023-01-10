package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.dtos.ProgressesDto;
import jinookk.ourlms.models.entities.Progress;
import jinookk.ourlms.services.ProgressService;
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

@WebMvcTest(ProgressController.class)
class ProgressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgressService progressService;

    @Test
    void progress() throws Exception {
        ProgressDto progressDto = Progress.fake("테스트 1강").toDto();

        given(progressService.detail(any(), any())).willReturn(progressDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/1/progress")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"status\":\"unwatched\"")
                ));
    }

    @Test
    void list() throws Exception {
        ProgressDto progressDto = Progress.fake("테스트 1강").toDto();

        ProgressesDto progressesDto = new ProgressesDto(List.of(progressDto));
        given(progressService.list(any(), date)).willReturn(progressesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/progresses")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"progresses\":[")
                ));
    }

    @Test
    void listByCourseId() throws Exception {
        ProgressDto progressDto = Progress.fake("테스트 1강").toDto();

        ProgressesDto progressesDto = new ProgressesDto(List.of(progressDto));
        given(progressService.listByCourseId(any())).willReturn(progressesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1/progresses"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"progresses\":[")
                ));
    }

    @Test
    void complete() throws Exception {
        Progress progress = Progress.fake("테스트 1강");
        progress.complete();

        ProgressDto progressDto = progress.toDto();

        given(progressService.complete(any())).willReturn(progressDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/progresses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"status\":\"completed\"")
                ));
    }

    @Test
    void updateTime() throws Exception {
        Progress progress = Progress.fake("테스트 1강");

        ProgressDto progressDto = progress.toDto();

        given(progressService.updateTime(any(), any())).willReturn(progressDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/progresses/1/time")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"minute\":1,\"second\":24}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"lectureTime\":{")
                ));
    }
}
