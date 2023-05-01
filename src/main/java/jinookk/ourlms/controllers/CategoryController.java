package jinookk.ourlms.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jinookk.ourlms.applications.category.CreateCategoryService;
import jinookk.ourlms.applications.category.DeleteCategoryService;
import jinookk.ourlms.applications.category.GetCategoryService;
import jinookk.ourlms.dtos.CategoriesDto;
import jinookk.ourlms.dtos.CategoryDto;
import jinookk.ourlms.dtos.CategoryRequestDto;
import jinookk.ourlms.exceptions.CategoryNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    private final GetCategoryService getCategoryService;
    private final CreateCategoryService createCategoryService;
    private final DeleteCategoryService deleteCategoryService;

    public CategoryController(GetCategoryService getCategoryService, CreateCategoryService createCategoryService, DeleteCategoryService deleteCategoryService) {
        this.getCategoryService = getCategoryService;
        this.createCategoryService = createCategoryService;
        this.deleteCategoryService = deleteCategoryService;
    }

    @GetMapping("categories")
    @ApiOperation(value = "Fetch Categories", notes = "fetch entire categories")
    public CategoriesDto list() {
        return getCategoryService.list();
    }

    @PostMapping("categories")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Category", notes = "create new category")
    public CategoryDto category(
            @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        return createCategoryService.post(categoryRequestDto);
    }

    @DeleteMapping("categories/{categoryId}")
    @ApiOperation(value = "Remove Category", notes = "removes category with id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "category not found", response = String.class)
    })
    public CategoryDto delete(
            @PathVariable Long categoryId
    ) {
        return deleteCategoryService.delete(categoryId);
    }

    @ExceptionHandler(CategoryNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String categoryNotFound(CategoryNotFound exception) {
        return exception.getMessage();
    }
}
