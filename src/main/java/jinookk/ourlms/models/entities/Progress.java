package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.dtos.ProgressDto;
import jinookk.ourlms.models.exceptions.InvalidArgument;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.status.ProgressStatus;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.models.vos.ids.LectureId;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Progress {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

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
    private ProgressStatus status = new ProgressStatus();

    @Embedded
    private LectureTime currentLectureTime = new LectureTime(0, 0);

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Progress() {
    }

    public Progress(Long id, CourseId courseId, AccountId accountId, LectureId lectureId, Title title) {
        this.id = id;
        this.courseId = courseId;
        this.accountId = accountId;
        this.lectureId = lectureId;
        this.title = title;
    }

    public static Progress fake(String lectureTitle) {
        return fake(new Title(lectureTitle));
    }

    private static Progress fake(Title title) {
        return new Progress(31L, new CourseId(1L), new AccountId(1L), new LectureId(1L), title);
    }

    public static Progress of(Lecture lecture, AccountId accountId) {
        if (lecture == null || accountId == null || accountId.value() == null) {
            throw new InvalidArgument();
        }

        return new Progress(null, lecture.courseId(), accountId, new LectureId(lecture.id()), lecture.title());
    }

    public static List<Progress> listOf(List<Lecture> lectures, AccountId accountId) {
        if (lectures == null || accountId == null || accountId.value() == null) {
            throw new InvalidArgument();
        }

        return lectures.stream()
                .map(lecture -> of(lecture, accountId))
                .toList();
    }

    public Long id() {
        return id;
    }

    public LectureTime lectureTime() {
        return currentLectureTime;
    }

    public Title title() {
        return title;
    }

    public ProgressDto toDto() {
        return new ProgressDto(id, lectureId, courseId, title, status, currentLectureTime, updatedAt);
    }

    public ProgressStatus status() {
        return status;
    }

    public void complete() {
        this.status.complete();
    }

    public Progress updateTime(LectureTimeDto lectureTimeDto) {
        this.currentLectureTime = new LectureTime(lectureTimeDto.getMinute(), lectureTimeDto.getSecond());

        return this;
    }
}
