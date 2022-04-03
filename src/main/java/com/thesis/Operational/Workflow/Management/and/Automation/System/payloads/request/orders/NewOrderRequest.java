package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.EStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewOrderRequest {

    private Long customerId;

    private List<BasketRequest> basket;

    private String status;
}
