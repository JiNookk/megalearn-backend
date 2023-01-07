package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.dtos.CartRequestDto;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CartServiceTest {
    CartService cartService;
    CartRepository cartRepository;

    @BeforeEach
    void setup() {
        cartRepository = mock(CartRepository.class);
        cartService = new CartService(cartRepository);

        Cart cart = Cart.fake(List.of(new CourseId(1L), new CourseId(2L)));

        given(cartRepository.findByAccountId(new AccountId(1L))).willReturn(Optional.of(cart));
    }

    @Test
    void detail() {
        CartDto cartDto = cartService.detail(new AccountId(1L));

        assertThat(cartDto.getItemIds()).hasSize(2);
    }

    @Test
    void addItem() {
        CartDto cartDto = cartService.addItem(
                new AccountId(1L), new CourseId(3L));

        assertThat(cartDto.getItemIds()).hasSize(3);
    }

    @Test
    void removeItem() {
        CartDto cartDto = cartService.removeItem(
                new AccountId(1L), new CartRequestDto(List.of(1L)));

        assertThat(cartDto.getItemIds()).hasSize(1);
    }
}