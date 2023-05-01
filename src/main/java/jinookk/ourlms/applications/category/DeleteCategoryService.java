package jinookk.ourlms.applications.category;

import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.exceptions.CategoryNotFound;
import jinookk.ourlms.models.entities.Category;
import jinookk.ourlms.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteCategoryService {
    private final CategoryRepository categoryRepository;

    public DeleteCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFound::new);

        categoryRepository.delete(category);

        return category.toDto();
    }
}
