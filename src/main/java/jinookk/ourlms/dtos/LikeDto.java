package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.ids.CourseId;

public class LikeDto {
    private Long id;
    private Boolean clicked;
    private Long courseId;

    public LikeDto() {
    }

    public LikeDto(Long id, Boolean clicked, CourseId courseId) {
        this.id = id;
        this.clicked = clicked;
        this.courseId = courseId.value();
    }

    public Boolean getClicked() {
        return clicked;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getId() {
        return id;
    }
}
