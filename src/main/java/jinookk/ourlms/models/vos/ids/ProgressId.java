package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class ProgressId {
    private final Long value;

    protected ProgressId() {
        this.value = null;
    }

    public ProgressId(Long value) {
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
                other.getClass().equals(ProgressId.class) &&
                ((ProgressId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
