package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.LectureDto;
import jinookk.ourlms.dtos.LectureRequestDto;
import jinookk.ourlms.dtos.LectureUpdateRequestDto;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.HandOutUrl;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.VideoUrl;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.SectionId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Lecture {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "status"))
    private Status status = new Status(Status.CREATED);

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "section_id"))
    private SectionId sectionId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "lecture_title"))
    private Title title;

    @Embedded
    private LectureTime lectureTime = new LectureTime(0, 0);

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "lecture_note"))
    private Content lectureNote;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "video_url"))
    private VideoUrl videoUrl;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "handout_url"))
    private HandOutUrl handOutUrl;


    public Lecture() {
    }

    public Lecture(Long id, CourseId courseId, SectionId sectionId, Title title, Content lectureNote,
                   HandOutUrl handOutUrl, VideoUrl videoUrl) {
        this.id = id;
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.title = title;
        this.lectureNote = lectureNote;
        this.handOutUrl = handOutUrl;
        this.videoUrl = videoUrl;
    }

    public static Lecture fake(String lectureTitle) {
        return fake(new Title(lectureTitle));
    }

    private static Lecture fake(Title title) {
        Long id = 1L;
        CourseId courseId = new CourseId(1L);
        Content lectureNote = new Content("lectureNote");
        SectionId sectionId = new SectionId(1L);
        HandOutUrl handOutUrl = new HandOutUrl("url");
        VideoUrl videoUrl = new VideoUrl("video/url");

        return new Lecture(id, courseId, sectionId, title, lectureNote, handOutUrl, videoUrl);
    }

    public static Lecture fake(LectureId lectureId) {
        Long id = lectureId.value();
        CourseId courseId = new CourseId(1L);
        Content lectureNote = new Content("lectureNote");
        SectionId sectionId = new SectionId(1L);
        Title title = new Title("title");
        HandOutUrl handOutUrl = new HandOutUrl("url");
        VideoUrl videoUrl = new VideoUrl("video/url");

        return new Lecture(id, courseId, sectionId, title, lectureNote, handOutUrl, videoUrl);
    }

    public static Lecture of(LectureRequestDto lectureRequestDto) {
        return new Lecture(
                null,
                new CourseId(lectureRequestDto.getCourseId()),
                new SectionId(lectureRequestDto.getSectionId()),
                new Title(lectureRequestDto.getTitle()),
                new Content(""),
                new HandOutUrl(""),
                new VideoUrl(""));
    }

    public Long id() {
        return id;
    }

    public CourseId courseId() {
        return courseId;
    }

    public Status status() {
        return status;
    }

    public Title title() {
        return title;
    }

    public VideoUrl videoUrl() {
        return videoUrl;
    }

    public LectureDto toLectureDto() {
        return new LectureDto(id, courseId, sectionId, title, videoUrl, lectureTime);
    }

    public void update(LectureUpdateRequestDto lectureUpdateRequestDto) {
        this.title.update(lectureUpdateRequestDto.getTitle());
        this.videoUrl.update(lectureUpdateRequestDto.getVideoUrl());
        this.lectureNote.update(lectureUpdateRequestDto.getLectureNote());
        this.handOutUrl.update(lectureUpdateRequestDto.getFilePath());
        this.lectureTime = lectureTime.update(lectureUpdateRequestDto.getLectureTime());
    }

    public void delete() {
        this.status.delete();
        this.courseId.delete();
        this.sectionId.delete();
        this.title.delete();
        this.videoUrl.delete();
        this.lectureNote.delete();
        this.handOutUrl.delete();
    }
}
