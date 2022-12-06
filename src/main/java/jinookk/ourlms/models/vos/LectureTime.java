package jinookk.ourlms.models.vos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class LectureTime {
    @Column(name = "minutes")
    private Long minute;

    @Column(name = "seconds")
    private Long second;

    public LectureTime() {
    }

    public LectureTime(Long minute, Long second) {
        this.minute = minute;
        this.second = second;
    }

    public Long minute() {
        return minute;
    }

    public Long second() {
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
