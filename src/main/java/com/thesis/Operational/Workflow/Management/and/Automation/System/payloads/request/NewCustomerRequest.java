package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCustomerRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private double balance;
}
