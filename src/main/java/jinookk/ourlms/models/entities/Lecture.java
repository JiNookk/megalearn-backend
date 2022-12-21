package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.VideoUrl;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//

@Entity
public class Lecture {
    @Id
    @GeneratedValue
    private Long id;
    private Long courseId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "lecture_title"))
    private Title title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "video_url"))
    private VideoUrl videoUrl;


    public Lecture() {
    }

    public Lecture(Long id, Long courseId, Title title, VideoUrl videoUrl) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.videoUrl = videoUrl;
    }

    public static Lecture fake(String lectureTitle) {
        Title title = new Title(lectureTitle);
        return fake(title);
    }

    private static Lecture fake(Title title) {
        Long id = 31L;
        Long courseId = 1L;
        VideoUrl videoUrl = new VideoUrl("video/url");

        return new Lecture(id, courseId, title, videoUrl);
    }

    public Long id() {
        return id;
    }

    public Long courseId() {
        return courseId;
    }

    public Title title() {
        return title;
    }

    public VideoUrl videoUrl() {
        return videoUrl;
    }

    public LectureDto toLectureDto() {
        return new LectureDto(id, courseId, title, videoUrl);
    }
}
