package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jinookk.ourlms.applications.cart.AddCartItemService;
import jinookk.ourlms.applications.cart.DeleteCartItemService;
import jinookk.ourlms.applications.cart.GetCartService;
import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.dtos.CartRequestDto;
import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CartItemNotFound;
import jinookk.ourlms.exceptions.CartNotFound;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.CourseId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    private final GetCartService getCartService;
    private final AddCartItemService addCartItemService;
    private final DeleteCartItemService deleteCartItemService;

    public CartController(GetCartService getCartService, AddCartItemService addCartItemService, DeleteCartItemService deleteCartItemService) {
        this.getCartService = getCartService;
        this.addCartItemService = addCartItemService;
        this.deleteCartItemService = deleteCartItemService;
    }

    @GetMapping("/carts/me")
    @ApiOperation(value = "my Cart", notes = "fetches my cart.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CartDto.class),
            @ApiResponse(code = 404, message = "Not found", response = SectionDto.class),
    })
    public CartDto myCart(
            @RequestAttribute UserName userName
    ) {
        return getCartService.detail(userName);
    }

    @PatchMapping("/carts/me/add-item/{itemId}")
    @ApiOperation(value = "add Item", notes = "add an item to my cart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CartDto addItem(
            @RequestAttribute UserName userName,
            @PathVariable Long itemId
    ) {
        return addCartItemService.addItem(userName, new CourseId(itemId));
    }

    @PatchMapping("/carts/me/remove-item")
    @ApiOperation(value = "remove Item", notes = "removes item in my cart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CartDto removeItem(
            @RequestAttribute UserName userName,
            @RequestBody CartRequestDto cartRequestDto
    ) {
        return deleteCartItemService.removeItem(userName, cartRequestDto);
    }

    @ExceptionHandler(CartNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cartNotFound(CartNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(CartItemNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cartItemNotFound(CartItemNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(AccountNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String accountNotFound(AccountNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNameRequired(ServletRequestBindingException exception) {
        return exception.getMessage();
    }
}
