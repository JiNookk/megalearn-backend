package jinookk.ourlms.models.entities;

import jinookk.ourlms.dtos.NoteDeleteDto;
import jinookk.ourlms.dtos.NoteDto;
import jinookk.ourlms.dtos.NoteRequestDto;
import jinookk.ourlms.dtos.NoteUpdateDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.ids.LectureId;
import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.status.Status;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Note {
    @Id @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "lecture_id"))
    private LectureId lectureId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "account_id"))
    private AccountId accountId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "status"))
    private Status status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "content"))
    private Content content;

    @Embedded
    private LectureTime lectureTime;

    @CreationTimestamp
    private LocalDateTime publishTime;

    public Note() {
    }

    public Note(Long id, LectureId lectureId, AccountId accountId, Status status, Content content,
                LectureTime lectureTime, LocalDateTime publishTime) {
        this.id = id;
        this.lectureId = lectureId;
        this.accountId = accountId;
        this.status = status;
        this.content = content;
        this.lectureTime = lectureTime;
        this.publishTime = publishTime;
    }

    public static Note of(NoteRequestDto noteRequestDto, AccountId accountId) {
        return new Note(null, new LectureId(noteRequestDto.getLectureId()), accountId,
                new Status(Status.CREATED), new Content(noteRequestDto.getContent()),
                new LectureTime(noteRequestDto.getLectureTime().getMinute(),
                        noteRequestDto.getLectureTime().getSecond()), LocalDateTime.now());
    }

    public Content content() {
        return content;
    }

    public Long id() {
        return id;
    }

    public LectureId lectureId() {
        return lectureId;
    }

    public LectureTime lectureTime() {
        return lectureTime;
    }

    public LocalDateTime publishTime() {
        return publishTime;
    }

    public NoteDto toNoteDto() {
        return new NoteDto(id, lectureId, lectureTime, content, publishTime);
    }

    public AccountId accountId() {
        return accountId;
    }

    public void delete() {
        this.status.delete();
    }

    public NoteDeleteDto toNoteDeleteDto() {
        return new NoteDeleteDto(id);
    }

    public void update(NoteUpdateDto noteUpdateDto) {
        this.content = new Content(noteUpdateDto.getContent());
    }
}
