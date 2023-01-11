package jinookk.ourlms.models.dtos;

import jinookk.ourlms.dtos.LectureTimeDto;
import jinookk.ourlms.models.vos.LectureTime;

public class LogDto {
    private Long lectureId;
    private String content;
    private LectureTimeDto lectureTime;

    public LogDto() {
    }

    public LogDto(Long lectureId, String content, LectureTime lectureTime) {
        this.lectureId = lectureId;
        this.content = content;
        this.lectureTime = new LectureTimeDto(lectureTime);
    }

    public Long getLectureId() {
        return lectureId;
    }

    public String getContent() {
        return content;
    }

    public LectureTimeDto getLectureTime() {
        return lectureTime;
    }
}
