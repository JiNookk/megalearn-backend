package jinookk.ourlms.applications.skill;

import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.exceptions.SkillNotFound;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.repositories.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteSkillService {
    private final SkillRepository skillRepository;

    public DeleteSkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public SkillDto delete(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(SkillNotFound::new);

        skillRepository.delete(skill);

        return skill.toDto();
    }
}
