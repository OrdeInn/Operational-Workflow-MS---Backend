package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EditBuildingRequest {

    private Long id;

    private String name;

    private String location;
}
