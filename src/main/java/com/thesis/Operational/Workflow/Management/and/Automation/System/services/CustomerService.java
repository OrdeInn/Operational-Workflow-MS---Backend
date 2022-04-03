package com.thesis.Operational.Workflow.Management.and.Automation.System.services;

import com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions.ResourceNotFoundException;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Customer;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.EditCustomerRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.NewCustomerRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.CustomerRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService<Customer, Long> {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        super(customerRepository, "Customer");
        this.customerRepository = customerRepository;
    }

    public Customer createNewCustomer(NewCustomerRequest request){

        Customer customer = new Customer();
        customer.setFName(request.getFirstName());
        customer.setLName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setBalance(request.getBalance());

        customer = customerRepository.save(customer);
        return customer;
    }

    public Customer editCustomer(EditCustomerRequest request){

        Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);

        if(customer != null){
            if(request.getFirstName() != null)
                customer.setFName(request.getFirstName());
            if(request.getLastName() != null)
                customer.setLName(request.getLastName());
            if(request.getEmail() != null)
                customer.setEmail(request.getEmail());
            if(request.getAddress() != null)
                customer.setAddress(request.getAddress());
            if(request.getBalance() >= 0)
                customer.setBalance(request.getBalance());
            return customerRepository.save(customer);
        }else {
            throw new ResourceNotFoundException("Customer", request.getCustomerId());
        }
    }
}
