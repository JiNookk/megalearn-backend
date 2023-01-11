package jinookk.ourlms.models.vos;

import jinookk.ourlms.models.dtos.LogDto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Log {
    private Long lectureId;

    private String content;

    @Column(name = "minutes")
    private Integer minute;

    @Column(name = "seconds")
    private Integer second;

    public Log() {
    }

    public Log(Long lectureId, String content, Integer minute, Integer second) {
        this.lectureId = lectureId;
        this.content = content;
        this.minute = minute;
        this.second = second;
    }

    public LogDto toDto() {
        return new LogDto(lectureId, content, new LectureTime(minute, second));
    }
}
