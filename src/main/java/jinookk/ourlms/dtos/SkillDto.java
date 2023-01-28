package jinookk.ourlms.dtos;

public class SkillDto {
    private Long id;
    private String content;

    public SkillDto() {
    }

    public SkillDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
