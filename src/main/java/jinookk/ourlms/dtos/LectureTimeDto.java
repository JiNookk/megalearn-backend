package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.LectureTime;

public class LectureTimeDto {
    private Long minute;
    private Long second;

    public LectureTimeDto() {
    }

    public LectureTimeDto(LectureTime lectureTime) {
        this.minute = lectureTime == null ? null : lectureTime.minute();
        this.second = lectureTime == null ? null : lectureTime.second();
    }

    public Long getMinute() {
        return minute;
    }

    public Long getSecond() {
        return second;
    }
}
