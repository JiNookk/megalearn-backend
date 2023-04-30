package jinookk.ourlms.models.vos;

import jinookk.ourlms.dtos.LectureTimeDto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class LectureTime {
    @Column(name = "minutes")
    private final Integer minute;

    @Column(name = "seconds")
    private final Integer second;

    protected LectureTime() {
        this.minute = null;
        this.second = null;
    }

    public LectureTime(Integer minute, Integer second) {
        this.minute = minute;
        this.second = second;
    }

    public Integer minute() {
        return minute;
    }

    public Integer second() {
        return second;
    }

    public LectureTimeDto toDto() {
        return new LectureTimeDto(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minute, second);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(LectureTime.class) &&
                ((LectureTime) other).minute != null &&
                ((LectureTime) other).second != null &&
                ((LectureTime) other).minute.equals(this.minute) &&
                ((LectureTime) other).second.equals(this.second);
    }

    @Override
    public String toString() {
        return "LectureTime minute: " + minute + ", second: " + second;
    }
}
