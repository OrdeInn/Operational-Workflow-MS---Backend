package com.thesis.Operational.Workflow.Management.and.Automation.System.repositories;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Order;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {
}
