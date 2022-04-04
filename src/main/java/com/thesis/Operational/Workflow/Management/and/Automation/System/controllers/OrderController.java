package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.*;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders.BasketRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders.EditOrderRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders.NewOrderRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.OrderResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items.ProductResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.CustomerService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.OrderService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.UserService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.items.ProductService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.specifications.OrderSpec;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private UserService userService;

    public OrderController(OrderService orderService, ProductService productService, CustomerService customerService, UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Page<OrderResponse>> getOrderPage(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "size") int size,
                                                            @RequestParam(name = "sort") String sort, Principal principal){

        User employee = userService.findByEMail(principal.getName());

        Specification<Order> spec = Specification.where(null);
        if(employee.hasRole(ERole.ROLE_FACTORY_WORKER)){
            spec = spec.and(OrderSpec.byStatus(EStatus.WAITING_FOR_PRODUCTION));
        }else if(employee.hasRole(ERole.ROLE_WAREHOUSE_WORKER)){
            Specification<Order> warehouseWorkerSpec =
                    OrderSpec.byStatus(EStatus.SHIPPING_TO_WAREHOUSE).or(OrderSpec.byStatus(EStatus.WAITING_FOR_DELIVER));
            spec = spec.and(warehouseWorkerSpec);
        }else if(employee.hasRole(ERole.ROLE_GRAPHICS_DESIGNER)){
            spec = spec.and(OrderSpec.byStatus(EStatus.WAITING_FOR_GRAPHICS_DESIGNER));
        }

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
        customer.setBalance(customer.getBalance()-order.getTotalCost());
        customerService.save(customer);

        return ResponseEntity.ok(new OrderResponse(order));
    }

    @PatchMapping("")
    public ResponseEntity<OrderResponse> editOrder(@RequestBody EditOrderRequest request){

        Order order = orderService.changeStatus(request);
        return ResponseEntity.ok(new OrderResponse(order));
    }
}
