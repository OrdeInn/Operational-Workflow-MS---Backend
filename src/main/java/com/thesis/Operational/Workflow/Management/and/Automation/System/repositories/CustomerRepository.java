package com.thesis.Operational.Workflow.Management.and.Automation.System.repositories;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Customer;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {
}
