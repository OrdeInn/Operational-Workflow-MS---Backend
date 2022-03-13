package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyResponse {

    private Long id;

    private String name;

    private CategoryResponse category;

    public SupplyResponse(Supply supply) {

        this.id = supply.getId();
        this.name = supply.getName();
        this.category = new CategoryResponse(supply.getCategory());
    }
}
