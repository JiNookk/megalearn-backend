package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.VideoUrl;

public class LectureDto {
    private Long id;
    private Long courseId;
    private String title;
    private String videoUrl;

    public LectureDto() {
    }

    public LectureDto(Long id, Long courseId, Title title, VideoUrl videoUrl) {
        this.id = id;
        this.courseId = courseId;
        this.title = title.value();
        this.videoUrl = videoUrl.value();
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

    public String getVideoUrl() {
        return videoUrl;
    }
}
