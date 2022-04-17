package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyBasketRequest {

    private Long supplyId;

    private int quantity;
}
