package jinookk.ourlms.models.vos;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Stars {
    private Double rating;

    public Stars() {
    }

    public Stars(Double rating) {
        this.rating = rating;
    }

    public Double rating() {
        return rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass().equals(Stars.class) &&
                ((Stars) other).rating.equals(this.rating);
    }

    @Override
    public String toString() {
        return "Stars rating: " + rating;
    }
}
