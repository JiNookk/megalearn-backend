package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CategoriesDto;
import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.dtos.CategoryRequestDto;
import jinookk.ourlms.models.entities.Category;
import jinookk.ourlms.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CategoryServiceTest {
    CategoryService categoryService;
    CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void post() {
        Category category = Category.fake("category");

        given(categoryRepository.save(any())).willReturn(category);

        CategoryDto categoryDto = categoryService.post(new CategoryRequestDto("category", "url"));

        assertThat(categoryDto).isNotNull();
    }

    @Test
    void list() {
        Category category = Category.fake("category");

        given(categoryRepository.findAll()).willReturn(List.of(category));

        CategoriesDto categoriesDto = categoryService.list();

        assertThat(categoriesDto.getCategories()).hasSize(1);
    }

    @Test
    void delete() {
        Category category = Category.fake("category");

        given(categoryRepository.findById(any())).willReturn(Optional.of(category));

        CategoryDto deleted = categoryService.delete(1L);

        assertThat(deleted).isNotNull();
    }
}
