package com.thesis.Operational.Workflow.Management.and.Automation.System.models.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "supply")
@Getter
@Setter
public class Supply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
