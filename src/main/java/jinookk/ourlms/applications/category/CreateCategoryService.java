package jinookk.ourlms.applications.category;

import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.dtos.CategoryRequestDto;
import jinookk.ourlms.models.entities.Category;
import jinookk.ourlms.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCategoryService {
    private final CategoryRepository categoryRepository;

    public CreateCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto post(CategoryRequestDto categoryRequestDto) {
        Category category = Category.of(categoryRequestDto);

        Category saved = categoryRepository.save(category);

        return saved.toDto();
    }
}
