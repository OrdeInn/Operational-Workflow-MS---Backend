package com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Category;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
}
