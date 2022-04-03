package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Customer;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Order;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders.BasketRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders.EditOrderRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders.NewOrderRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.OrderResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items.ProductResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.CustomerService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.OrderService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.items.ProductService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Order Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;
    private ProductService productService;
    private CustomerService customerService;

    public OrderController(OrderService orderService, ProductService productService, CustomerService customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<Page<OrderResponse>> getOrderPage(@RequestParam(name = "page") int page,
                                                                @RequestParam(name = "size") int size,
                                                                @RequestParam(name = "sort") String sort){

        Specification<Order> spec = Specification.where(null);

        Page<Order> orderPage = orderService.findAll(spec, page, size, sort);

        List<OrderResponse> responseList = new ArrayList<>();
        orderPage.forEach(order -> {
            responseList.add(new OrderResponse(order));
        });

        Page<OrderResponse> responsePage = new PageImpl<>(responseList, orderPage.getPageable(), orderPage.getTotalElements());

        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("")
    public ResponseEntity<OrderResponse> placeNewOrder(@RequestBody NewOrderRequest request){
        Customer customer = customerService.findById(request.getCustomerId());
        Map<Product, Integer> basket = new HashMap<>();

        for (BasketRequest basketRequest : request.getBasket()) {
            Product product = productService.findById(basketRequest.getProductId());
            basket.put(product, basketRequest.getQuantity());
        }

        Order order = orderService.createNewOrder(request, basket, customer);

        return ResponseEntity.ok(new OrderResponse(order));
    }

    @PatchMapping("")
    public ResponseEntity<OrderResponse> editOrder(@RequestBody EditOrderRequest request){

        Order order = orderService.changeStatus(request);
        return ResponseEntity.ok(new OrderResponse(order));
    }
}
