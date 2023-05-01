package jinookk.ourlms.applications.category;

import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.models.entities.Category;
import jinookk.ourlms.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeleteCategoryServiceTest {
    DeleteCategoryService deleteCategoryService;
    CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        categoryRepository = mock(CategoryRepository.class);
        deleteCategoryService = new DeleteCategoryService(categoryRepository);
    }

    @Test
    void delete() {
        Category category = Category.fake("category");

        given(categoryRepository.findById(any())).willReturn(Optional.of(category));

        CategoryDto deleted = deleteCategoryService.delete(1L);

        assertThat(deleted).isNotNull();
    }
}
