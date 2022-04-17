package com.thesis.Operational.Workflow.Management.and.Automation.System.services;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.ESupplyRequestStatus;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.SupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Factory;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Warehouse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.NewSupplyRequestPayload;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.SupplyRequestRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.base.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class SupplyRequestService extends BaseService<SupplyRequest, Long> {

    private SupplyRequestRepository supplyRequestRepository;

    public SupplyRequestService(SupplyRequestRepository supplyRequestRepository) {
        super(supplyRequestRepository, "Supply Request");
        this.supplyRequestRepository = supplyRequestRepository;
    }

    public SupplyRequest createSupplyRequest(Factory factory, NewSupplyRequestPayload payload, Map<Supply, Integer> requestBasket){

        if(factory != null){

            Warehouse warehouse = factory.getWarehouse();
            boolean isOutOfStock = false;

            for(Supply supply : requestBasket.keySet()){
                if(warehouse.isOutOfStock(supply, requestBasket.get(supply)))
                    isOutOfStock = true;
            }

            SupplyRequest supplyRequest = new SupplyRequest();
            supplyRequest.setCreationDate(LocalDateTime.now());
            supplyRequest.setFactory(factory);
            supplyRequest.setRequestBasket(requestBasket);

            if(!isOutOfStock){
                supplyRequest.setStatus(ESupplyRequestStatus.WAITING_WAREHOUSE);
            } else {
                supplyRequest.setStatus(ESupplyRequestStatus.OUT_OF_STOCK);
            }

            return supplyRequestRepository.save(supplyRequest);
        } else {
            return null;
        }
    }

    public SupplyRequest completeSupplyRequest(Long requestId){

        SupplyRequest supplyRequest = supplyRequestRepository.findById(requestId).orElse(null);
        if(supplyRequest != null){
            supplyRequest.setStatus(ESupplyRequestStatus.DONE);
            supplyRequest.setCompletionDate(LocalDateTime.now());

            return supplyRequestRepository.save(supplyRequest);
        } else {
            return null;
        }
    }
}
