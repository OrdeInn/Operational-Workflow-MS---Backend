package com.thesis.Operational.Workflow.Management.and.Automation.System.services.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions.ResourceNotFoundException;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Category;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.EditCategoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.NewCategoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.items.CategoryRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<Category, Long> {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository, "Category");
        this.categoryRepository = categoryRepository;
    }

    public Category createNewCategory(NewCategoryRequest request){

        Category category = new Category();
        category.setName(request.getName());

        return categoryRepository.save(category);
    }

    public Category editCategory(EditCategoryRequest request){

        Category category = categoryRepository.findById(request.getId()).orElse(null);

        if(category != null){

            if(request.getName() != null)
                category.setName(request.getName());

            return categoryRepository.save(category);
        }else {

            throw new ResourceNotFoundException("Category", request.getId());
        }
    }
}
