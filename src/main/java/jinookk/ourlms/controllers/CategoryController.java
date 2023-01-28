package jinookk.ourlms.controllers;

import jinookk.ourlms.dtos.CategoriesDto;
import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.dtos.CategoryRequestDto;
import jinookk.ourlms.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("categories")
    public CategoriesDto list() {
        return categoryService.list();
    }

    @PostMapping("categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto category(
            @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        return categoryService.post(categoryRequestDto);
    }

    @DeleteMapping("categories/{categoryId}")
    public CategoryDto delete(
            @PathVariable Long categoryId
    ) {
        return categoryService.delete(categoryId);
    }
}
