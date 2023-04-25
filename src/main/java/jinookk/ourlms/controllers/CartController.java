package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.dtos.CartRequestDto;
import jinookk.ourlms.dtos.SectionDto;
import jinookk.ourlms.exceptions.AccountNotFound;
import jinookk.ourlms.exceptions.CartNotFound;
import jinookk.ourlms.models.vos.Name;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.CartService;
import org.springframework.http.HttpStatus;
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
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
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
            @RequestAttribute Name userName
    ) {
        return cartService.detail(userName);
    }

    @PatchMapping("/carts/me/add-item/{itemId}")
    @ApiOperation(value = "add Item", notes = "add an item to my cart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CartDto addItem(
            @RequestAttribute Name userName,
            @PathVariable Long itemId
    ) {
        return cartService.addItem(userName, new CourseId(itemId));
    }

    @PatchMapping("/carts/me/remove-item")
    @ApiOperation(value = "remove Item", notes = "removes item in my cart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer {access_token}", required = true, dataType = "string", paramType = "header")
    })
    public CartDto removeItem(
            @RequestAttribute Name userName,
            @RequestBody CartRequestDto cartRequestDto
    ) {
        return cartService.removeItem(userName, cartRequestDto);
    }

    @ExceptionHandler(CartNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cartNotFound(CartNotFound error) {
        return error.getMessage();
    }

    @ExceptionHandler(AccountNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String accountNotFound(AccountNotFound error) {
        return error.getMessage();
    }
}
