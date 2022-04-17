package com.thesis.Operational.Workflow.Management.and.Automation.System.models;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.base.BaseEntity;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Factory;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "supply_request")
@Getter
@Setter
public class SupplyRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Factory factory;

    @ElementCollection
    private Map<Supply, Integer> requestBasket = new HashMap<>();

    private LocalDateTime creationDate;

    private LocalDateTime completionDate;

    private ESupplyRequestStatus status;
}
