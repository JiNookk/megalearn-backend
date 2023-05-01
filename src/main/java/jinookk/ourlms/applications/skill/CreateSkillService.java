package jinookk.ourlms.applications.skill;

import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.dtos.SkillRequestDto;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.repositories.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateSkillService {
    private final SkillRepository skillRepository;

    public CreateSkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public SkillDto post(SkillRequestDto skillRequestDto) {
        Skill skill = Skill.of(skillRequestDto);

        Skill saved = skillRepository.save(skill);

        return saved.toDto();
    }
}
