package com.thesis.Operational.Workflow.Management.and.Automation.System.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ERole name;

    @Column(name = "name_tr")
    private String nameTr;

    public Role() {

    }

    public Role(ERole name) {

        this.name = name;
    }
}