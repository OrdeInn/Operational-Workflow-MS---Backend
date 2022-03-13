package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private Long id;

    private String name;

    public CategoryResponse(Category category){

        this.id = category.getId();
        this.name = category.getName();
    }
}
