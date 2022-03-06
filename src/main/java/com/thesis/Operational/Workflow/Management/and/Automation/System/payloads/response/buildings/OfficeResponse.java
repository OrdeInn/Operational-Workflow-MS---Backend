package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Office;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class OfficeResponse extends BuildingResponse {

    private Set<UserResponse> employees;

    public OfficeResponse(Office office) {

        super(office);
        this.employees = office.getEmployees().stream().map(UserResponse::new).collect(Collectors.toSet());
    }
}
