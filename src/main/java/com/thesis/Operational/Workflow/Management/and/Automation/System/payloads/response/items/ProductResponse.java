package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long id;

    private String name;

    private CategoryResponse category;

    private double price;

    public ProductResponse(Product product) {

        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.category = new CategoryResponse(product.getCategory());
    }
}
