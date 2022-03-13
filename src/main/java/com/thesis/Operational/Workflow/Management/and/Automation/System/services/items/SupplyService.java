package com.thesis.Operational.Workflow.Management.and.Automation.System.services.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions.ResourceNotFoundException;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Category;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.EditSupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.NewSupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.items.SupplyRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class SupplyService extends BaseService<Supply, Long> {

    private final SupplyRepository supplyRepository;

    public SupplyService(SupplyRepository supplyRepository) {
        super(supplyRepository, "Supply");
        this.supplyRepository = supplyRepository;
    }

    public Supply createNewSupply(NewSupplyRequest request, Category category){

        Supply supply = new Supply();
        supply.setName(request.getName());
        supply.setCategory(category);

        return supplyRepository.save(supply);
    }

    public Supply editSupply(EditSupplyRequest request, Category category){

        Supply supply = supplyRepository.findById(request.getId()).orElse(null);

        if(supply != null){

            if(request.getName() != null)
                supply.setName(request.getName());

            if(category != null)
                supply.setCategory(category);

            return supplyRepository.save(supply);
        }else {
            throw new ResourceNotFoundException("Supply", request.getId());
        }
    }
}
