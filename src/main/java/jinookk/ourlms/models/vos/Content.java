package jinookk.ourlms.models.vos;

import javax.persistence.Column;
import java.util.Objects;

public class Content {
    @Column(length = 1000)
    private String value;

    public Content() {
    }

    public Content(String value) {
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
                other.getClass().equals(Content.class) &&
                ((Content) other).value.equals(this.value);
    }
    @Override
    public String toString() {
        return "Content value: " + value;
    }
}
