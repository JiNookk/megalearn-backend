package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CategoriesDto;
import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.dtos.SkillRequestDto;
import jinookk.ourlms.dtos.SkillsDto;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.repositories.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SkillServiceTest {
    SkillService skillService;
    SkillRepository skillRepository;

    @BeforeEach
    void setup() {
        skillRepository = mock(SkillRepository.class);
        skillService = new SkillService(skillRepository);
    }

    @Test
    void post() {
        Skill skill = Skill.fake("skill");

        given(skillRepository.save(any())).willReturn(skill);

        SkillDto skillDto = skillService.post(new SkillRequestDto("skill"));

        assertThat(skillDto).isNotNull();
    }

    @Test
    void list() {
        Skill skill = Skill.fake("skill");

        given(skillRepository.findAll()).willReturn(List.of(skill));

        SkillsDto skillsDto = skillService.list();

        assertThat(skillsDto.getSkills()).hasSize(1);
    }

    @Test
    void delete() {
        Skill skill = Skill.fake("skill");

        given(skillRepository.findById(any())).willReturn(Optional.of(skill));

        SkillDto deleted = skillService.delete(1L);

        assertThat(deleted).isNotNull();
    }
}
