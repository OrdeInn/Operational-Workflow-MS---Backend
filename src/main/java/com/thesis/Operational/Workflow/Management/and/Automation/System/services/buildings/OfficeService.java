package com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Office;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.EditOfficeRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.NewOfficeRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.buildings.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OfficeService extends BuildingService{

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        super(officeRepository);
        this.officeRepository = officeRepository;
    }

    public Office createNewOffice(NewOfficeRequest request){

        Office office = new Office();
        office.setName(request.getName());
        office.setLocation(request.getLocation());

        return officeRepository.save(office);
    }

    public Office editOffice(EditOfficeRequest request, Set<User> employees){

        Office office = officeRepository.findById(request.getId()).orElse(null);

        if(office != null){

            if(request.getName() != null)
                office.setName(request.getName());

            if(request.getLocation() != null)
                office.setLocation(request.getLocation());

            if(employees != null && !employees.isEmpty())
                office.setEmployees(employees);

            return officeRepository.save(office);
        }

        return null;
    }
}
