package jinookk.ourlms.models.vos;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class StudentCount {
    private Long value;

    public StudentCount() {
    }

    public StudentCount(Long value) {
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
                other.getClass().equals(StudentCount.class) &&
                ((StudentCount) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "StudentCount value: " + value;
    }
}
