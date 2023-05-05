package jinookk.ourlms.applications.cart;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.fixtures.Fixture;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
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

class GetCartServiceTest extends Fixture {
    GetCartService getCartService;
    CartRepository cartRepository;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        cartRepository = mock(CartRepository.class);
        getCartService = new GetCartService(cartRepository, accountRepository);

        List<CourseId> courseIds = List.of(new CourseId(1L), new CourseId(2L));

        Cart cart = Fixture.cart(new AccountId(1L));

        for (CourseId courseId : courseIds) {
            cart = cart.addItem(courseId.value());
        }

        given(cartRepository.findByAccountId(new AccountId(1L))).willReturn(Optional.of(cart));

        Account account = Fixture.account("account");
        given(accountRepository.findByUserName(any())).willReturn(Optional.of(account));
    }

    @Test
    void detail() {
        CartDto cartDto = getCartService.detail(new UserName("userName@email.com"));

        assertThat(cartDto.getItemIds()).hasSize(2);
    }
}