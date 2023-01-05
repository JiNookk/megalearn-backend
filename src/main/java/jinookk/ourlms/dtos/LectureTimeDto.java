package jinookk.ourlms.dtos;

import jinookk.ourlms.models.vos.LectureTime;

public class LectureTimeDto {
    private Integer minute;
    private Integer second;

    public LectureTimeDto() {
    }

    public LectureTimeDto(LectureTime lectureTime) {
        this.minute = lectureTime == null ? null : lectureTime.minute();
        this.second = lectureTime == null ? null : lectureTime.second();
    }

    public Integer getMinute() {
        return minute;
    }

    public Integer getSecond() {
        return second;
    }
}
