package com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Warehouse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.EditWarehouseRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.NewWarehouseRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings.BuildingRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WarehouseService extends BuildingService<Warehouse, Long>{

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(BuildingRepository<Warehouse, Long> buildingRepository, WarehouseRepository warehouseRepository) {
        super(buildingRepository);
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createNewWarehouse(NewWarehouseRequest request){

        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());

        return warehouseRepository.save(warehouse);
    }

    public Warehouse editWarehouse(EditWarehouseRequest request, Set<User> employees){

        Warehouse warehouse = warehouseRepository.findById(request.getId()).orElse(null);

        if(warehouse != null){

            if(request.getName() != null)
                warehouse.setName(request.getName());

            if(request.getLocation() != null)
                warehouse.setLocation(request.getLocation());

            if(employees != null && !employees.isEmpty())
                warehouse.setEmployees(employees);

            return warehouseRepository.save(warehouse);
        }

        return null;
    }
}
