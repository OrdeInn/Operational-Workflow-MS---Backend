package com.thesis.Operational.Workflow.Management.and.Automation.System.repositories;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.SupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplyRequestRepository extends BaseRepository<SupplyRequest, Long> {
}
