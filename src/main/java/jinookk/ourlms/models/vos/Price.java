package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidPrice;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Price {
    private Integer value;

    public Price() {
    }

    public Price(int value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    public void update(Integer value) {
        if (value < 10000 && value != 0) {
            throw new InvalidPrice(value);
        }

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
                other.getClass().equals(Price.class) &&
                ((Price) other).value.equals(this.value);
    }

    @Override
    public String toString() {
        return "Price value: " + value;
    }
}
