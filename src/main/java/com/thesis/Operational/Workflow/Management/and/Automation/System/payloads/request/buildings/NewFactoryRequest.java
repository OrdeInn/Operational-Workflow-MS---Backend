package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewFactoryRequest extends NewBuildingRequest{

    private Long warehouseId;
}
