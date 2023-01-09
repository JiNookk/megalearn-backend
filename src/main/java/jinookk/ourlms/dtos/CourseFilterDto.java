package jinookk.ourlms.dtos;

public class CourseFilterDto {
    private String level;
    private String cost;
    private String skill;
    private String content;

    public CourseFilterDto() {
    }

    public CourseFilterDto(String level, String cost, String skill, String content) {
        this.level = level;
        this.cost = cost;
        this.skill = skill;
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public String getCost() {
        return cost;
    }

    public String getSkill() {
        return skill;
    }

    public String getContent() {
        return content;
    }
}
