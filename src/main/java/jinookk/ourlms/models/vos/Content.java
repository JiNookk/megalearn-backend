package jinookk.ourlms.models.vos;

import java.time.LocalDateTime;
import java.util.Objects;

public class Content {
    private String value;

    public Content() {
    }

    public Content(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public void update(String content) {
        this.value = content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(Content.class) &&
                ((Content) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "Content value: " + value;
    }
}
