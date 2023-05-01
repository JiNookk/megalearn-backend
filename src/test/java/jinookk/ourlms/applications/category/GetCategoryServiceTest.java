package jinookk.ourlms.applications.category;

import jinookk.ourlms.dtos.CategoriesDto;
import jinookk.ourlms.models.entities.Category;
import jinookk.ourlms.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetCategoryServiceTest {
    GetCategoryService getCategoryService;
    CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        categoryRepository = mock(CategoryRepository.class);
        getCategoryService = new GetCategoryService(categoryRepository);
    }

    @Test
    void list() {
        Category category = Category.fake("category");

        given(categoryRepository.findAll()).willReturn(List.of(category));

        CategoriesDto categoriesDto = getCategoryService.list();

        assertThat(categoriesDto.getCategories()).hasSize(1);
    }
}
