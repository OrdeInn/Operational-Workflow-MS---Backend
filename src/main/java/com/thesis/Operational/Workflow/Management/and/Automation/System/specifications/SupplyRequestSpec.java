package com.thesis.Operational.Workflow.Management.and.Automation.System.specifications;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.SupplyRequest;
import org.springframework.data.jpa.domain.Specification;

public class SupplyRequestSpec {

    public static Specification<SupplyRequest> byFactory(Long factoryId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("factory").get("id"), factoryId);
    }
}
