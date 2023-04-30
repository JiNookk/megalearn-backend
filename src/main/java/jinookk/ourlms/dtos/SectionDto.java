package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.status.Status;

public class SectionDto {
    private Long id;
    private Long courseId;
    private String title;
    private String content;
    private String status;

    public SectionDto() {
    }

    public SectionDto(Long id, CourseId courseId, Title title, Content content, String status) {
        this.id = id;
        this.courseId = courseId.value();
        this.title = title.value();
        this.content = content.value();
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }
}
