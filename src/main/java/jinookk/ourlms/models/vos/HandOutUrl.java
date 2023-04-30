package jinookk.ourlms.models.vos;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class HandOutUrl {
    private final String value;

    protected HandOutUrl() {
        this.value = null;
    }

    public HandOutUrl(String value) {
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
                other.getClass().equals(HandOutUrl.class) &&
                ((HandOutUrl) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "HandOutUrl: " + value;
    }
}
