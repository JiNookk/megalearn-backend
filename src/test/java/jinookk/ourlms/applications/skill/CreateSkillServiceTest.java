package jinookk.ourlms.applications.skill;

import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.dtos.SkillRequestDto;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.repositories.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CreateSkillServiceTest {
    CreateSkillService createSkillService;
    SkillRepository skillRepository;

    @BeforeEach
    void setup() {
        skillRepository = mock(SkillRepository.class);
        createSkillService = new CreateSkillService(skillRepository);
    }

    @Test
    void post() {
        Skill skill = Skill.fake("skill");

        given(skillRepository.save(any())).willReturn(skill);

        SkillDto skillDto = createSkillService.post(new SkillRequestDto("skill"));

        assertThat(skillDto).isNotNull();
    }
}
