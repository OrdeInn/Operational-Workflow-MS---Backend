package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private double totalPrice;

    private LocalDateTime creationDate;

    private LocalDateTime completionDate;

    private String status;


    private List<BasketResponse> basket = new ArrayList<>();

    private CustomerResponse customer;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalCost();
        this.creationDate = order.getCreationDate();
        this.completionDate = order.getCompletionDate();
        this.status = order.getStatus().toString();
        this.customer = new CustomerResponse(order.getCustomer());

        order.getBasket().keySet().forEach(product -> {
            basket.add(new BasketResponse(product, order.getBasket().get(product)));
        });
    }
}
