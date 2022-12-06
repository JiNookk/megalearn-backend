package jinookk.ourlms.models.vos;

import java.util.Objects;

public class Like {
    private String value;

    public Like() {
    }

    public Like(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(Like.class) &&
                ((Like) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "Like value: " + value;
    }
}
