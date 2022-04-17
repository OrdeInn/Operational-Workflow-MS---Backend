package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Factory;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class FactoryResponse extends BuildingResponse{

    private Set<UserResponse> employees;

    private String warehouse;

    public FactoryResponse(Factory factory) {
        super(factory);
        this.employees = factory.getEmployees().stream().map(UserResponse::new).collect(Collectors.toSet());
        this.warehouse = factory.getWarehouse().getName();
    }
}
