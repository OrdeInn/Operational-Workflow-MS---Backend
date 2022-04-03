package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditOrderRequest {

    private Long orderId;

    private String status;
}
