package jinookk.ourlms.models.vos;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ImagePath {
    private final String value;

    protected ImagePath() {
        this.value = null;
    }

    public ImagePath(String value) {
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
                other.getClass().equals(ImagePath.class) &&
                ((ImagePath) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "ImagePath value: " + value;
    }
}
