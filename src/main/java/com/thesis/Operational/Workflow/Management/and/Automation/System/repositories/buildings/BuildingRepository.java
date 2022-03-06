package com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Building;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface BuildingRepository<T extends Building, ID extends Serializable> extends BaseRepository<T, ID> {
}
