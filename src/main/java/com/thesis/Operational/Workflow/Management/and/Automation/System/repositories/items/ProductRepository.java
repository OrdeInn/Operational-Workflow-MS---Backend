package com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
}
