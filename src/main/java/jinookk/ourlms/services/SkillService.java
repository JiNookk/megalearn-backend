package jinookk.ourlms.services;

import jinookk.ourlms.dtos.SkillsDto;
import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.dtos.SkillRequestDto;
import jinookk.ourlms.exceptions.SkillNotFound;
import jinookk.ourlms.models.entities.Skill;
import jinookk.ourlms.repositories.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public SkillDto post(SkillRequestDto skillRequestDto) {
        Skill skill = Skill.of(skillRequestDto);

        Skill saved = skillRepository.save(skill);

        return saved.toDto();
    }

    public SkillsDto list() {
        List<Skill> categories = skillRepository.findAll();

        List<SkillDto> skillDtos = categories.stream()
                .map(Skill::toDto)
                .toList();

        return new SkillsDto(skillDtos);
    }

    public SkillDto delete(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(SkillNotFound::new);

        skillRepository.delete(skill);

        return skill.toDto();
    }
}
