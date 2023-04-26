package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.dtos.CartRequestDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CartServiceTest {
    CartService cartService;
    CartRepository cartRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        cartRepository = mock(CartRepository.class);
        cartService = new CartService(cartRepository, accountRepository);

        List<CourseId> courseIds = List.of(new CourseId(1L), new CourseId(2L));

        Cart cart = Cart.fake(new AccountId(1L));

        for (CourseId courseId : courseIds) {
            cart = cart.addItem(courseId.value());
        }

        given(cartRepository.findByAccountId(new AccountId(1L))).willReturn(Optional.of(cart));

        Account account = Account.fake("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void detail() {
        CartDto cartDto = cartService.detail(new UserName("userName@email.com"));

        assertThat(cartDto.getItemIds()).hasSize(2);
    }

    @Test
    void addItem() {
        CartDto cartDto = cartService.addItem(
                new UserName("userName@email.com"), new CourseId(3L));

        assertThat(cartDto.getItemIds()).hasSize(3);
    }

    @Test
    void removeItem() {
        CartDto cartDto = cartService.removeItem(
                new UserName("userName@email.com"), new CartRequestDto(List.of(1L)));

        assertThat(cartDto.getItemIds()).hasSize(1);
    }
}