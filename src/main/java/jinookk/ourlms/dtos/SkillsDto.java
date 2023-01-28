package jinookk.ourlms.dtos;

import java.util.List;

public class SkillsDto {
    private List<SkillDto> skills;

    public SkillsDto() {
    }

    public SkillsDto(List<SkillDto> skills) {
        this.skills = skills;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }
}
