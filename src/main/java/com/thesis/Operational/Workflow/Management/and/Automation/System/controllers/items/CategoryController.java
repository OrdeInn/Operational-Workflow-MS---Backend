package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Category;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.EditCategoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.NewCategoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items.CategoryResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.items.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Category Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<Page<CategoryResponse>> getCategoryPage(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "size") int size,
                                                            @RequestParam(name = "sort") String sort){

        Specification<Category> spec = Specification.where(null);

        Page<Category> categoryPage = categoryService.findAll(spec, page, size, sort);

        List<CategoryResponse> responseList = new ArrayList<>();
        categoryPage.forEach(category -> {
            responseList.add(new CategoryResponse(category));
        });

        Page<CategoryResponse> responsePage = new PageImpl<>(responseList, categoryPage.getPageable(), categoryPage.getTotalElements());

        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("")
    public ResponseEntity<CategoryResponse> createNewCategory(@RequestBody NewCategoryRequest request){

        Category category = categoryService.createNewCategory(request);

        return ResponseEntity.ok(new CategoryResponse(category));
    }

    @PatchMapping("")
    public ResponseEntity<CategoryResponse> editCategory(@RequestBody EditCategoryRequest request){

        Category category = categoryService.editCategory(request);

        return ResponseEntity.ok(new CategoryResponse(category));
    }
}
