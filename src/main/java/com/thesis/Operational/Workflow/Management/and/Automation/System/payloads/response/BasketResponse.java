package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items.ProductResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketResponse {

    private ProductResponse productResponse;

    private int quantity;

    public BasketResponse(Product product, int quantity) {
        this.productResponse = new ProductResponse(product);
        this.quantity = quantity;
    }
}
