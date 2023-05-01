package jinookk.ourlms.applications.category;

import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.dtos.CategoryRequestDto;
import jinookk.ourlms.models.entities.Category;
import jinookk.ourlms.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CreateCategoryServiceTest {
    CreateCategoryService createCategoryService;
    CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        categoryRepository = mock(CategoryRepository.class);
        createCategoryService = new CreateCategoryService(categoryRepository);
    }

    @Test
    void post() {
        Category category = Category.fake("category");

        given(categoryRepository.save(any())).willReturn(category);

        CategoryDto categoryDto = createCategoryService.post(new CategoryRequestDto("category", "url"));

        assertThat(categoryDto).isNotNull();
    }
}
