package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class CourseId {
    private final Long value;

    protected CourseId() {
        this.value = null;
    }

    public CourseId(Long value) {
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
                other.getClass().equals(CourseId.class) &&
                ((CourseId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "CourseId value: " + value;
    }
}
