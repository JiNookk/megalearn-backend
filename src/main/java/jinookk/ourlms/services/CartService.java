package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.dtos.CartRequestDto;
import jinookk.ourlms.exceptions.CartNotFound;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartDto detail(AccountId accountId) {
        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        return cart.toDto();
    }

    public CartDto addItem(AccountId accountId, CourseId itemId) {
        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        cart.addItem(itemId.value());

        return cart.toDto();
    }

    public CartDto removeItem(AccountId accountId, CartRequestDto cartRequestDto) {
        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFound(accountId));

        List<CourseId> courseIds = cartRequestDto.getItemIds().stream().map(CourseId::new).toList();

        cart.removeItems(courseIds);

        return cart.toDto();
    }
}
