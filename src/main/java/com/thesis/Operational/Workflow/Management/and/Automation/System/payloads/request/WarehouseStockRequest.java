package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WarehouseStockRequest {

    private Long warehouseId;

    private List<SupplyBasketRequest> stock;
}
