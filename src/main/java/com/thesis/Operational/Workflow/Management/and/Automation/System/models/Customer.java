package com.thesis.Operational.Workflow.Management.and.Automation.System.models;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fName;

    private String lName;

    private String email;

    private String address;

    private double balance;
}
