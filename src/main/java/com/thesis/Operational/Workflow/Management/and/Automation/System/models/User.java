package com.thesis.Operational.Workflow.Management.and.Automation.System.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.base.BaseEntity;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Building;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fName;

    private String lName;

    private String email;

    private boolean enabled = false;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    private Building building;

    public boolean hasRole(ERole role) {

        return roles.stream().anyMatch(role1 -> role1.getName().equals(role));
    }
}
