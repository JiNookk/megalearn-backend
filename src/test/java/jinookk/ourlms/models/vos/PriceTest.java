package jinookk.ourlms.models.vos;

import jinookk.ourlms.exceptions.InvalidPrice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {
    @Test
    void updateWithValidPrice() {
        Price price = new Price(0);

        price.update(100000);

        assertThat(price.value()).isEqualTo(100000);
    }

    @Test
    void updateWithInvalidPrice() {
        Price price = new Price(0);

        assertThrows(InvalidPrice.class, () -> {
            price.update(-5);
        });

        assertThrows(InvalidPrice.class, () -> {
            price.update(100);
        });
    }
}