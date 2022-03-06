package com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Building;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings.BuildingRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.base.BaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class BuildingService<T extends Building, ID extends Serializable> extends BaseService<T, ID> {

    private final BuildingRepository<T, ID> buildingRepository;

    public BuildingService(BuildingRepository<T, ID> buildingRepository) {
        super(buildingRepository, "Building");
        this.buildingRepository = buildingRepository;
    }
}
