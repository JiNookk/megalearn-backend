package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.models.vos.Log;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Note {
    @Id @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "course_id"))
    private CourseId courseId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "account_id"))
    private AccountId accountId;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Log> logs = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime publishTime;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Note() {
    }

    public Note(Long id, CourseId courseId, AccountId accountId) {
        this.id = id;
        this.courseId = courseId;
        this.accountId = accountId;
    }

    public static Note fake(Long courseId) {
        return fake(new CourseId(courseId));
    }

    private static Note fake(CourseId courseId) {
        return new Note(1L, courseId, new AccountId(1L));
    }

    public static Note of(NoteRequestDto noteRequestDto, AccountId accountId) {
        return new Note(null, new CourseId(noteRequestDto.getLectureId()), accountId);
    }

    public Long id() {
        return id;
    }

    public LocalDateTime publishTime() {
        return publishTime;
    }

    public NoteDto toNoteDto() {
        return new NoteDto(id, courseId, logs, publishTime, updatedAt);
    }

    public AccountId accountId() {
        return accountId;
    }

    public void delete() {
        this.courseId = null;
        this.accountId = null;
        this.publishTime = null;
        this.updatedAt = null;
        this.logs = null;
    }

    public NoteDeleteDto toNoteDeleteDto() {
        return new NoteDeleteDto(id);
    }

//    public void update(NoteUpdateDto noteUpdateDto) {
//        this.content.update(noteUpdateDto.getContent());
//    }
}
