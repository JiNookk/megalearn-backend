package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class LectureId {
    private Long value;

    public LectureId() {
    }

    public LectureId(Long value) {
        this.value = value;
    }


    public Long value() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(LectureId.class) &&
                ((LectureId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "LectureId value: " + value;
    }
}
