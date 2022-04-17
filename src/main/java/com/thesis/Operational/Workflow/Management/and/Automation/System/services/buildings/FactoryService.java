package com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.ESupplyRequestStatus;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.SupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Factory;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Warehouse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.NewSupplyRequestPayload;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.EditFactoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.NewFactoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.SupplyRequestRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings.FactoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Service
public class FactoryService extends BuildingService<Factory, Long> {

    private final FactoryRepository factoryRepository;
    private final SupplyRequestRepository supplyRequestRepository;

    public FactoryService(FactoryRepository factoryRepository, SupplyRequestRepository supplyRequestRepository) {
        super(factoryRepository);
        this.factoryRepository = factoryRepository;
        this.supplyRequestRepository = supplyRequestRepository;
    }

    public Factory createNewFactory(NewFactoryRequest request, Warehouse warehouse){

        Factory factory = new Factory();
        factory.setName(request.getName());
        factory.setLocation(request.getLocation());
        factory.setWarehouse(warehouse);
        return factoryRepository.save(factory);
    }

    public Factory editFactory(EditFactoryRequest request, Set<User> employees, Warehouse warehouse){

        Factory factory = findById(request.getId());

        if(factory != null){

            if(request.getName() != null)
                factory.setName(request.getName());

            if(request.getLocation() != null)
                factory.setLocation(request.getLocation());

            if(employees != null && !employees.isEmpty())
                factory.setEmployees(employees);

            if(warehouse != null)
                factory.setWarehouse(warehouse);

            return factoryRepository.save(factory);
        }

        return null;
    }
}
