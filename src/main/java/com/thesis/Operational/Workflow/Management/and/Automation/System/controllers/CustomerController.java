package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Customer;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.EditCustomerRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.NewCustomerRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.CustomerResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.CustomerService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Customer Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<Page<CustomerResponse>> getCustomerPage(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "size") int size,
                                                            @RequestParam(name = "sort") String sort){

        Specification<Customer> spec = Specification.where(null);

        Page<Customer> customerPage = customerService.findAll(spec, page, size, sort);

        List<CustomerResponse> responseList = new ArrayList<>();
        customerPage.forEach(customer -> {
            responseList.add(new CustomerResponse(customer));
        });

        Page<CustomerResponse> responsePage = new PageImpl<>(responseList, customerPage.getPageable(), customerPage.getTotalElements());
        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("")
    public ResponseEntity<CustomerResponse> createNewCustomer(@RequestBody NewCustomerRequest request){

        Customer customer = customerService.createNewCustomer(request);
        return ResponseEntity.ok(new CustomerResponse(customer));
    }

    @PatchMapping("")
    public ResponseEntity<CustomerResponse> editCustomer(@RequestBody EditCustomerRequest request){

        Customer customer = customerService.editCustomer(request);
        return ResponseEntity.ok(new CustomerResponse(customer));
    }
}
