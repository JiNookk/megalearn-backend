package jinookk.ourlms.applications.skill;

import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.dtos.SkillsDto;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.repositories.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GetSkillService {
    private final SkillRepository skillRepository;

    public GetSkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public SkillsDto list() {
        List<Skill> categories = skillRepository.findAll();

        List<SkillDto> skillDtos = categories.stream()
                .map(Skill::toDto)
                .toList();

        return new SkillsDto(skillDtos);
    }
}
