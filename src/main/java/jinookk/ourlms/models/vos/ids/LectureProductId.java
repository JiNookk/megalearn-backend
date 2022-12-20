package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class LectureProductId {
    private Long value;

    public LectureProductId() {
    }

    public LectureProductId(Long value) {
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
                other.getClass().equals(LectureProductId.class) &&
                ((LectureProductId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "LectureProductId value: " + value;
    }
}
