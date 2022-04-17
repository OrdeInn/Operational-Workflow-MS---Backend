package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items.SupplyResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplyBasketResponse {

    private SupplyResponse supply;

    private int quantity;

    public SupplyBasketResponse(Supply supply, int quantity) {
        this.supply = new SupplyResponse(supply);
        this.quantity = quantity;
    }
}
