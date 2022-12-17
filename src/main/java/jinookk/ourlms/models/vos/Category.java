package jinookk.ourlms.models.vos;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Category {
    private String value;

    public Category() {
    }

    public Category(String value) {
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
                other.getClass().equals(Category.class) &&
                ((Category) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "Category value: " + value;
    }
}
