package com.thesis.Operational.Workflow.Management.and.Automation.System.services;

import com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions.ResourceNotFoundException;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Customer;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.EStatus;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Order;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders.EditOrderRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders.NewOrderRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.OrderRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.base.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class OrderService extends BaseService<Order, Long> {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        super(orderRepository, "Order");
        this.orderRepository = orderRepository;
    }

    public Order createNewOrder(NewOrderRequest request, Map<Product, Integer> basket, Customer customer){

        Order order = new Order();

        order.setStatus(EStatus.fromString(request.getStatus()));
        order.setCreationDate(LocalDateTime.now());
        order.setCompletionDate(null);
        order.setBasket(basket);
        order.setCustomer(customer);

        double totalPrice = calculateTotalCost(basket);
        order.setTotalCost(totalPrice);

        return orderRepository.save(order);
    }

    public Order changeStatus(EditOrderRequest request){
        Order order = orderRepository.findById(request.getOrderId()).orElse(null);
        if(order !=null ){
            if(request.getStatus() != null)
                order.setStatus(EStatus.fromString(request.getStatus()));

            return orderRepository.save(order);
        }else {
            throw new ResourceNotFoundException("Order", request.getOrderId());
        }
    }

    private double calculateTotalCost(Map<Product, Integer> basket){
        double totalCost = 0;

        for(Product product : basket.keySet()){
            totalCost += product.getPrice() * basket.get(product);
        }

        return totalCost;
    }
}
