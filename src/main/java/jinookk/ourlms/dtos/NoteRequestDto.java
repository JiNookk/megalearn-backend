package jinookk.ourlms.dtos;

public class NoteRequestDto {
    private String content;
    private Long lectureId;
    private LectureTimeDto lectureTime;

    public NoteRequestDto() {
    }

    public NoteRequestDto(String content, Long lectureId, LectureTimeDto lectureTime) {
        this.content = content;
        this.lectureId = lectureId;
        this.lectureTime = lectureTime;
    }

    public String getContent() {
        return content;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public LectureTimeDto getLectureTime() {
        return lectureTime;
    }
}
