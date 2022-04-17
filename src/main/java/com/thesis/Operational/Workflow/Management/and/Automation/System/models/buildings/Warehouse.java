package com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "warehouse")
@Getter
@Setter
public class Warehouse extends Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "building")
    private Set<User> employees = new HashSet<>();

    @ElementCollection
    private Map<Supply, Integer> stock = new HashMap<>();

    @OneToMany(mappedBy = "warehouse")
    private Set<Factory> responsibleFactories;

    public boolean isOutOfStock(Supply supply, Integer amount){
        if (this.stock.containsKey(supply) && this.stock.get(supply) >= amount){
            return false;
        } else {
            return true;
        }
    }
}
