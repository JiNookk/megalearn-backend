package jinookk.ourlms.models.entities;

import jinookk.ourlms.exceptions.CartItemNotFound;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartTest {
    @Test
    void update() {
        Cart cart = new Cart(new AccountId(1L));

        Cart updated = cart.addItem(2L);

        assertThat(updated.itemIds()).hasSize(1);
    }

    @Test
    void updateWithDuplicatedId() {
        Cart cart = new Cart(new AccountId(1L));

        Cart updated = cart.addItem(1L);

        assertThat(updated.itemIds()).hasSize(1);
    }

    @Test
    void updateWithNullValue() {
        Cart cart = new Cart(new AccountId(1L));

        Cart updated = cart.addItem(null);

        assertThat(updated.itemIds()).hasSize(0);
    }

    @Test
    void removeItems() {
        Cart cart = new Cart(new AccountId(1L));

        cart.addItem(1L);
        cart.addItem(2L);
        cart.addItem(3L);

        Cart removed = cart.removeItems(List.of(new CourseId(3L)));

        assertThat(removed.itemIds()).isEqualTo(List.of(new CourseId(1L), new CourseId(2L)));
    }

    @Test
    void removeItemsWithNull() {
        Cart cart = new Cart(new AccountId(1L));

        cart.addItem(1L);
        cart.addItem(2L);
        cart.addItem(3L);

        Cart removed = cart.removeItems(null);

        assertThat(removed.itemIds()).isEqualTo(List.of(new CourseId(1L), new CourseId(2L), new CourseId(3L)));
    }

    @Test
    void removeItemWithValidValue() {
        Cart cart = new Cart(new AccountId(1L));

        cart.addItem(1L);
        cart.addItem(2L);
        cart.addItem(3L);

        Cart removed = cart.removeItem(new CourseId(1L));

        assertThat(removed.itemIds()).isEqualTo(List.of( new CourseId(2L), new CourseId(3L)));
    }

    @Test
    void removeItemWithNull() {
        Cart cart = new Cart(new AccountId(1L));

        cart.addItem(1L);
        cart.addItem(2L);
        cart.addItem(3L);

        assertThrows(CartItemNotFound.class, () -> {
            cart.removeItem(null);
        });
    }

    @Test
    void removeItemWithNotExistingValue() {
        Cart cart = new Cart(new AccountId(1L));

        cart.addItem(1L);

        assertThrows(CartItemNotFound.class, () -> {
            cart.removeItem(new CourseId(2L));
        });
    }
}
