package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.SkillsDto;
import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.services.SkillService;
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

@WebMvcTest(SkillController.class)
class SkillControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService skillService;

    @Test
    void post() throws Exception {
        SkillDto skillDto = Skill.fake("skill").toDto();

        given(skillService.post(any())).willReturn(skillDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"skill\":\"skill\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"content\":\"skill\"")
                ));
    }

    @Test
    void list() throws Exception {
        SkillDto skillDto = Skill.fake("skill").toDto();

        given(skillService.list()).willReturn(new SkillsDto(List.of(skillDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/skills"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"skills\":[")
                ));
    }

    @Test
    void delete() throws Exception {
        SkillDto skillDto = Skill.fake("skill").toDto();

        given(skillService.delete(any())).willReturn(skillDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/skills/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"content\":\"skill\"")
                ));
    }
}
