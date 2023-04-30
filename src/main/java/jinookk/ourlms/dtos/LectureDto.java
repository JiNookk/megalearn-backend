package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.VideoUrl;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.SectionId;
import jinookk.ourlms.models.vos.status.Status;

public class LectureDto {
    private String status;
    private Long id;
    private Long courseId;
    private Long sectionId;
    private String title;
    private String videoUrl;
    private LectureTimeDto lectureTime;

    public LectureDto() {
    }

    public LectureDto(Long id, CourseId courseId, SectionId sectionId, Title title,
                      VideoUrl videoUrl, LectureTime lectureTime, Status status) {
        this.id = id;
        this.courseId = courseId.value();
        this.sectionId = sectionId.value();
        this.title = title.value();
        this.videoUrl = videoUrl.value();
        this.lectureTime = lectureTime.toDto();
        this.status = status.value();
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public LectureTimeDto getLectureTime() {
        return lectureTime;
    }

    public String getStatus() {
        return status;
    }
}
