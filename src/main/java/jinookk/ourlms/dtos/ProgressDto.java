package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.status.ProgressStatus;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.LectureId;

import java.time.LocalDateTime;

public class ProgressDto {
    private Long id;
    private Long lectureId;
    private Long courseId;
    private String title;
    private String status;
    private LectureTimeDto lectureTime;
    private LocalDateTime completedAt;
    private LocalDateTime updatedAt;

    public ProgressDto() {
    }

    public ProgressDto(Long id, LectureId lectureId, CourseId courseId, Title title, ProgressStatus status,
                       LectureTime lectureTime, LocalDateTime updatedAt) {
        this.id = id;
        this.lectureId = lectureId.value();
        this.courseId = courseId.value();
        this.title = title.value();
        this.status = status.value();
        this.completedAt = status.completedAt();
        this.lectureTime = new LectureTimeDto(lectureTime);
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public LectureTimeDto getLectureTime() {
        return lectureTime;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
