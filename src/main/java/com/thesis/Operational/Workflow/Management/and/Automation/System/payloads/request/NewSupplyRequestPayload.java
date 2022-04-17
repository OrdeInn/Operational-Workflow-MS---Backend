package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewSupplyRequestPayload {

    private Long factoryId;

    private List<SupplyBasketRequest> request;
}
