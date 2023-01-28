package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.SkillDto;
import jinookk.ourlms.dtos.SkillRequestDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Skill {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    public Skill() {
    }

    public Skill(String content) {
        this.content = content;
    }

    public static Skill of(SkillRequestDto skillRequestDto) {
        return new Skill(skillRequestDto.getSkillTag());
    }

    public static Skill fake(String skill) {
        return new Skill(skill);
    }

    public SkillDto toDto() {
        return new SkillDto(id, content);
    }
}
