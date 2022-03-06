package com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class Building extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String location;
}
