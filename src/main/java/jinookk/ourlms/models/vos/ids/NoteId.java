package jinookk.ourlms.models.vos.ids;

import java.util.Objects;

public class NoteId {
    private final Long value;

    protected NoteId() {
        this.value = null;
    }

    public NoteId(Long value) {
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
                other.getClass().equals(NoteId.class) &&
                ((NoteId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
