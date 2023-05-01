package jinookk.ourlms.applications.cart;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CartNotFound;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddCartItemService {
    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;

    public AddCartItemService(CartRepository cartRepository, AccountRepository accountRepository) {
        this.cartRepository = cartRepository;
        this.accountRepository = accountRepository;
    }

    public CartDto addItem(UserName userName, CourseId itemId) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        cart.addItem(itemId.value());

        return cart.toDto();
    }
}
