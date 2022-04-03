package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private Long customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private double balance;

    public CustomerResponse(Customer customer){
        this.customerId = customer.getId();
        this.firstName = customer.getFName();
        this.lastName = customer.getLName();
        this.email = customer.getEmail();
        this.address = customer.getEmail();
        this.balance = customer.getBalance();
    }
}
