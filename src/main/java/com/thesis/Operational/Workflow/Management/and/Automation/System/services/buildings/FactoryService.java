package com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Factory;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.EditFactoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.NewFactoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings.FactoryRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FactoryService extends BuildingService<Factory, Long> {

    private final FactoryRepository factoryRepository;

    public FactoryService(FactoryRepository factoryRepository) {
        super(factoryRepository);
        this.factoryRepository = factoryRepository;
    }

    public Factory createNewFactory(NewFactoryRequest request){

        Factory factory = new Factory();
        factory.setName(request.getName());
        factory.setLocation(request.getLocation());

        return factoryRepository.save(factory);
    }

    public Factory editFactory(EditFactoryRequest request, Set<User> employees){

        Factory factory = factoryRepository.findById(request.getId()).orElse(null);

        if(factory != null){

            if(request.getName() != null)
                factory.setName(request.getName());

            if(request.getLocation() != null)
                factory.setLocation(request.getLocation());

            if(employees != null && !employees.isEmpty())
                factory.setEmployees(employees);

            return factoryRepository.save(factory);
        }

        return null;
    }
}
