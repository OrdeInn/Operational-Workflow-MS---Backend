package com.thesis.Operational.Workflow.Management.and.Automation.System.models;


import com.thesis.Operational.Workflow.Management.and.Automation.System.models.base.BaseEntity;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalCost;

    private LocalDateTime creationDate;

    private LocalDateTime completionDate;

    private EStatus status;

    @ElementCollection
    private Map<Product, Integer> basket = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
