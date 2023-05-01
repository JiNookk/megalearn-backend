package jinookk.ourlms.applications.cart;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.dtos.CartRequestDto;
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

import java.util.List;

@Service
@Transactional
public class DeleteCartItemService {
    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;

    public DeleteCartItemService(CartRepository cartRepository, AccountRepository accountRepository) {
        this.cartRepository = cartRepository;
        this.accountRepository = accountRepository;
    }

    public CartDto removeItem(UserName userName, CartRequestDto cartRequestDto) {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFound(userName));

        AccountId accountId = new AccountId(account.id());

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        List<CourseId> courseIds = cartRequestDto.getItemIds().stream()
                .map(CourseId::new)
                .toList();

        cart.removeItems(courseIds);

        return cart.toDto();
    }
}
