package com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.SupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Warehouse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.WarehouseStockRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.EditWarehouseRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.NewWarehouseRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.SupplyRequestRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings.BuildingRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class WarehouseService extends BuildingService<Warehouse, Long>{

    private final WarehouseRepository warehouseRepository;
    private final SupplyRequestRepository supplyRequestRepository;

    public WarehouseService(BuildingRepository<Warehouse, Long> buildingRepository, WarehouseRepository warehouseRepository,
                            SupplyRequestRepository supplyRequestRepository) {
        super(buildingRepository);
        this.warehouseRepository = warehouseRepository;
        this.supplyRequestRepository = supplyRequestRepository;
    }

    public Warehouse createNewWarehouse(NewWarehouseRequest request){

        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());

        return warehouseRepository.save(warehouse);
    }

    public Warehouse addNewStock(WarehouseStockRequest request, Map<Supply, Integer> addedStock){

        Warehouse warehouse = findById(request.getWarehouseId());
        if(warehouse != null){

            for (Supply supply : addedStock.keySet()){
                warehouse.getStock().put(supply, addedStock.get(supply));
            }

            return save(warehouse);
        } else {
            return null;
        }

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
