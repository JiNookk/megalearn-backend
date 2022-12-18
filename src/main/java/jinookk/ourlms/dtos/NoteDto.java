package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.Content;
import jinookk.ourlms.models.vos.LectureTime;

import java.time.LocalDateTime;

public class NoteDto {
    private Long id;
    private String content;
    private LectureTimeDto lectureTime;
    private LocalDateTime publishTime;

    public NoteDto() {
    }

    public NoteDto(Long id, LectureTime lectureTime, Content content, LocalDateTime publishTime) {
        this.id = id;
        this.lectureTime = new LectureTimeDto(lectureTime);
        this.content = content.value();
        this.publishTime = publishTime;
    }

    public Long getId() {
        return id;
    }

    public LectureTimeDto getLectureTime() {
        return lectureTime;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public String getContent() {
        return content;
    }
}
