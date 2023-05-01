package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.cart.AddCartItemService;
import jinookk.ourlms.applications.cart.DeleteCartItemService;
import jinookk.ourlms.applications.cart.GetCartService;
import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCartService getCartService;

    @MockBean
    private AddCartItemService addCartItemService;

    @MockBean
    private DeleteCartItemService deleteCartItemService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String accessToken;

    CartDto cartDto;

    @BeforeEach
    void setup() {
        accessToken = jwtUtil.encode(new UserName("userName@email.com"));

        List<CourseId> courseIds = List.of(new CourseId(1L), new CourseId(2L));

        Cart cart = Cart.fake(new AccountId(1L));

        for (CourseId courseId : courseIds) {
            cart = cart.addItem(courseId.value());
        }

        cartDto = cart.toDto();
    }

    @Test
    void myCart() throws Exception {
        given(getCartService.detail(any()))
                .willReturn(cartDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"itemIds\":[")
                ));
    }

    @Test
    void update() throws Exception {
        given(addCartItemService.addItem(any(), any()))
                .willReturn(cartDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts/me/add-item/1")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"itemIds\":[")
                ));
    }

    @Test
    void removeItem() throws Exception {
        given(deleteCartItemService.removeItem(any(), any()))
                .willReturn(cartDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts/me/remove-item")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemIds\":[1, 2, 3]}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"itemIds\":[")
                ));
    }
}
