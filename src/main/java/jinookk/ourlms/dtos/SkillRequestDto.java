package jinookk.ourlms.dtos;

public class SkillRequestDto {
    private String skillTag;

    public SkillRequestDto() {
    }

    public SkillRequestDto(String skillTag) {
        this.skillTag = skillTag;
    }

    public String getSkillTag() {
        return skillTag;
    }
}
