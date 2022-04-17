package com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.SupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "factory")
@Getter
@Setter
public class Factory extends Building{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "building")
    private Set<User> employees = new HashSet<>();

    @OneToMany(mappedBy = "factory")
    private Set<SupplyRequest> supplyRequests;

    @ManyToOne
    private Warehouse warehouse;
}
