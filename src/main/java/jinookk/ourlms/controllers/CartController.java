package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.dtos.CartRequestDto;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts/me")
    public CartDto myCart(
            @RequestAttribute Long accountId
    ) {
        return cartService.detail(new AccountId(accountId));
    }

    @PatchMapping("/carts/me/add-item/{itemId}")
    public CartDto addItem(
            @RequestAttribute Long accountId,
            @PathVariable Long itemId
    ) {
        return cartService.addItem(new AccountId(accountId), new CourseId(itemId));
    }

    @PatchMapping("/carts/me/remove-item")
    public CartDto removeItem(
            @RequestAttribute Long accountId,
            @RequestBody CartRequestDto cartRequestDto
    ) {
        return cartService.removeItem(new AccountId(accountId), cartRequestDto);
    }
}
