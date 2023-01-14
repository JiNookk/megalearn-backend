package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class LikeId {
    private Long value;

    public LikeId() {
    }

    public LikeId(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(NoteId.class) &&
                ((LikeId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
