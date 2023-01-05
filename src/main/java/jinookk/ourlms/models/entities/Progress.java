package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.ids.SectionId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Progress {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "section_id"))
    private SectionId sectionId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "account_id"))
    private AccountId accountId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "lecture_id"))
    private LectureId lectureId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "lecture_title"))
    private Title title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "status"))
    private Status status;

    @Embedded
    private LectureTime lectureTime;

    public Progress() {
    }

    public Progress(Long id, CourseId courseId, SectionId sectionId, AccountId accountId,
                    LectureId lectureId, Title title, Status status, LectureTime lectureTime) {
        this.id = id;
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.accountId = accountId;
        this.lectureId = lectureId;
        this.title = title;
        this.status = status;
        this.lectureTime = lectureTime;
    }

    public static Progress fake(String lectureTitle) {
        return fake(new Title(lectureTitle));
    }

    private static Progress fake(Title title) {
        return new Progress(31L, new CourseId(1L), new SectionId(1L), new AccountId(1L), new LectureId(1L),
                title, new Status(Status.UNWATCHED), new LectureTime());
    }

    public Long id() {
        return id;
    }

    public SectionId sectionId() {
        return sectionId;
    }

    public Title title() {
        return title;
    }

    public ProgressDto toDto() {
        return new ProgressDto(id, lectureId, courseId, title, status, lectureTime);
    }

    public Status status() {
        return status;
    }

    public void complete() {
        this.status.complete();
    }
}
