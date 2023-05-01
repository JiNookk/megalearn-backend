package jinookk.ourlms.controllers;

import jinookk.ourlms.applications.category.CreateCategoryService;
import jinookk.ourlms.applications.category.DeleteCategoryService;
import jinookk.ourlms.applications.category.GetCategoryService;
import jinookk.ourlms.dtos.CategoriesDto;
import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.models.entities.Category;
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

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCategoryService getCategoryService;

    @MockBean
    private CreateCategoryService createCategoryService;

    @MockBean
    private DeleteCategoryService deleteCategoryService;

    @Test
    void post() throws Exception {
        CategoryDto categoryDto = Category.fake("category").toDto();

        given(createCategoryService.post(any())).willReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"category\":\"category\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"content\":\"category\"")
                ));
    }

    @Test
    void list() throws Exception {
        CategoryDto categoryDto = Category.fake("category").toDto();

        given(getCategoryService.list()).willReturn(new CategoriesDto(List.of(categoryDto)));

        mockMvc.perform(MockMvcRequestBuilders.get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"categories\":[")
                ));
    }

    @Test
    void delete() throws Exception {
        CategoryDto categoryDto = Category.fake("category").toDto();

        given(deleteCategoryService.delete(any())).willReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"content\":\"category\"")
                ));
    }
}
