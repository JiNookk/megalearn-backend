package jinookk.ourlms.models.vos;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class HandOutUrl {
    private String value;

    public HandOutUrl() {
    }

    public HandOutUrl(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public void update(String value) {
        this.value = value;
    }

    public void delete() {
        this.value = null;
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
