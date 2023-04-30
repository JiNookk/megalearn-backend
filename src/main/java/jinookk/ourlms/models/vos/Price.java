package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidPrice;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Price {
    private final Integer value;

    protected Price() {
        this.value = null;
    }

    public Price(int value) {
        if (isValidPrice(value)) {
            throw new InvalidPrice(value);
        }

        this.value = value;
    }

    public Integer value() {
        return value;
    }

    private boolean isValidPrice(Integer value) {
        return value < 10000 && value != 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(Price.class) &&
                ((Price) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "Price value: " + value;
    }
}
