package jinookk.ourlms.dtos;

public class CourseRequestDto {
    private String title;
    private String level;

    public CourseRequestDto() {
    }

    public CourseRequestDto(String title, String level) {
        this.title = title;
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public String getLevel() {
        return level;
    }
}
