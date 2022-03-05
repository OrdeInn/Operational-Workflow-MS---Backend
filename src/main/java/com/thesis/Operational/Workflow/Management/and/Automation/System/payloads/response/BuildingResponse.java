package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Building;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingResponse {

    private Long id;

    private String name;

    private String location;

    public BuildingResponse(Building building) {
        this.id = building.getId();
        this.name = building.getName();
        this.location = building.getLocation();
    }
}
