package jinookk.ourlms.models.vos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class LectureTime {
    @Column(name = "minutes")
    private Integer minute;

    @Column(name = "seconds")
    private Integer second;

    public LectureTime() {
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

    @Override
    public int hashCode() {
        return Objects.hash(minute, second);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(LectureTime.class) &&
                ((LectureTime) other).minute.equals(this.minute) &&
                ((LectureTime) other).second.equals(this.second);
    }

    @Override
    public String toString() {
        return "LectureTime minute: " + minute + ", second: " + second;
    }
}
