package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.LectureTime;
import jinookk.ourlms.models.vos.status.Status;
import jinookk.ourlms.models.vos.Title;
import jinookk.ourlms.models.vos.ids.LectureId;

public class ProgressDto {
    private Long id;
    private Long lectureId;
    private String title;
    private String status;
    private LectureTimeDto lectureTime;

    public ProgressDto() {
    }

    public ProgressDto(Long id, LectureId lectureId, Title title, Status status, LectureTime lectureTime) {
        this.id = id;
        this.lectureId = lectureId.value();
        this.title = title.value();
        this.status = status.value();
        this.lectureTime = new LectureTimeDto(lectureTime);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public LectureTimeDto getLectureTime() {
        return lectureTime;
    }

    public Long getLectureId() {
        return lectureId;
    }
}
