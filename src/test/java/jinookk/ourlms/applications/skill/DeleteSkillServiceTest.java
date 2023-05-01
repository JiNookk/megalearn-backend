package jinookk.ourlms.applications.skill;

import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.repositories.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeleteSkillServiceTest {
    DeleteSkillService deleteSkillService;
    SkillRepository skillRepository;

    @BeforeEach
    void setup() {
        skillRepository = mock(SkillRepository.class);
        deleteSkillService = new DeleteSkillService(skillRepository);
    }

    @Test
    void delete() {
        Skill skill = Skill.fake("skill");

        given(skillRepository.findById(any())).willReturn(Optional.of(skill));

        SkillDto deleted = deleteSkillService.delete(1L);

        assertThat(deleted).isNotNull();
    }
}
