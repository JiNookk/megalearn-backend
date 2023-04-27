package jinookk.ourlms.dtos;

import javax.validation.constraints.NotNull;

public class CourseRequestDto {
    @NotNull
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
