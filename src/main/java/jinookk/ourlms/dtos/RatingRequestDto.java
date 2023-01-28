package jinookk.ourlms.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RatingRequestDto {
    @NotNull
    private Long courseId;

    @NotNull
    private Integer rating;

    @NotBlank
    private String content;

    public RatingRequestDto() {
    }

    public RatingRequestDto(Long courseId, Integer rating, String content) {
        this.courseId = courseId;
        this.rating = rating;
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public Long getCourseId() {
        return courseId;
    }
}
