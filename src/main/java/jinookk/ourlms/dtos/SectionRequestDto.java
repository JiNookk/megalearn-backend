package jinookk.ourlms.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SectionRequestDto {
    @NotNull
    private Long courseId;

    @NotBlank
    private String title;

    @NotNull
    private String goal;

    public SectionRequestDto() {
    }

    public SectionRequestDto(Long courseId, String title, String goal) {
        this.courseId = courseId;
        this.title = title;
        this.goal = goal;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getGoal() {
        return goal;
    }
}
