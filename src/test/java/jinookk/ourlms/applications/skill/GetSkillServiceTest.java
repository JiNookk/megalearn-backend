package jinookk.ourlms.applications.skill;

import jinookk.ourlms.dtos.SkillsDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.repositories.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetSkillServiceTest {
    GetSkillService getSkillService;
    SkillRepository skillRepository;

    @BeforeEach
    void setup() {
        skillRepository = mock(SkillRepository.class);
        getSkillService = new GetSkillService(skillRepository);
    }

    @Test
    void list() {
        Skill skill = Fixture.skill("skill");

        given(skillRepository.findAll()).willReturn(List.of(skill));

        SkillsDto skillsDto = getSkillService.list();

        assertThat(skillsDto.getSkills()).hasSize(1);
    }
}
