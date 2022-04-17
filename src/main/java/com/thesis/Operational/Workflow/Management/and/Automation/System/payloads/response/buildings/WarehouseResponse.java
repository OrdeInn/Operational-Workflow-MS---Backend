package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Factory;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Warehouse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.SupplyBasketResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class WarehouseResponse extends BuildingResponse{

    private Set<UserResponse> employees;

    private List<String> responsibleFactories = new ArrayList<>();

    private List<SupplyBasketResponse> stock = new ArrayList<>();

    public WarehouseResponse(Warehouse warehouse) {
        super(warehouse);
        this.employees = warehouse.getEmployees().stream().map(UserResponse::new).collect(Collectors.toSet());

        for(Factory factory : warehouse.getResponsibleFactories()){
            responsibleFactories.add(factory.getName());
        }

        for (Supply supply : warehouse.getStock().keySet()){
            stock.add(new SupplyBasketResponse(supply, warehouse.getStock().get(supply)));
        }
    }
}
