package jinookk.ourlms.models.vos;

import java.util.Objects;

public class CommentId {
    private Long value;

    public CommentId() {
    }

    public CommentId(Long value) {
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
                other.getClass().equals(CommentId.class) &&
                ((CommentId) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "CommentId value: " + value;
    }
}
