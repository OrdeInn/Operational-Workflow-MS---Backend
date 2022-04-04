package com.thesis.Operational.Workflow.Management.and.Automation.System.specifications;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.EStatus;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpec {

    public static Specification<Order> byStatus(EStatus status) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}
