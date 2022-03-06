package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Warehouse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class WarehouseResponse extends BuildingResponse{

    private Set<UserResponse> employees;

    public WarehouseResponse(Warehouse warehouse) {
        super(warehouse);
        this.employees = warehouse.getEmployees().stream().map(UserResponse::new).collect(Collectors.toSet());
    }
}
