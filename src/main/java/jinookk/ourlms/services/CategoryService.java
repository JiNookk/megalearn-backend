package jinookk.ourlms.services;

import jinookk.ourlms.dtos.CategoriesDto;
import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.dtos.CategoryRequestDto;
import jinookk.ourlms.exceptions.CategoryNotFound;
import jinookk.ourlms.models.entities.Category;
import jinookk.ourlms.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto post(CategoryRequestDto categoryRequestDto) {
        Category category = Category.of(categoryRequestDto);

        Category saved = categoryRepository.save(category);

        return saved.toDto();
    }

    public CategoriesDto list() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryDto> categoryDtos = categories.stream()
                .map(Category::toDto)
                .toList();

        return new CategoriesDto(categoryDtos);
    }

    public CategoryDto delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFound::new);

        categoryRepository.delete(category);

        return category.toDto();
    }
}
