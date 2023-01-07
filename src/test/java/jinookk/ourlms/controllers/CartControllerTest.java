package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.CartDto;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.services.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    private CartService cartService;

    @Test
    void myCart() throws Exception {
        CartDto cartDto = Cart.fake(List.of(new CourseId(1L), new CourseId(2L))).toDto();

        given(cartService.detail(any()))
                .willReturn(cartDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/me")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"itemIds\":[")
                ));
    }

    @Test
    void update() throws Exception {
        CartDto cartDto = Cart.fake(List.of(new CourseId(1L), new CourseId(2L), new CourseId(3L))).toDto();

        given(cartService.addItem(any(), any()))
                .willReturn(cartDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts/me/add-item/1")
                        .header("Authorization", "Bearer ACCESS.TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"itemIds\":[")
                ));
    }

    @Test
    void removeItem() throws Exception {
        CartDto cartDto = Cart.fake(List.of(new CourseId(1L), new CourseId(2L), new CourseId(3L))).toDto();

        given(cartService.removeItem(any(), any()))
                .willReturn(cartDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts/me/remove-item")
                        .header("Authorization", "Bearer ACCESS.TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"itemIds\":[1, 2, 3]}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"itemIds\":[")
                ));
    }
}
