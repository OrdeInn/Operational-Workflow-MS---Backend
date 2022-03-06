package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EditFactoryRequest extends EditBuildingRequest{

    private Set<Long> employees;

}
