package com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.SupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.buildings.FactoryResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SupplyRequestResponse {

    private Long id;

    private FactoryResponse factory;

    private List<SupplyBasketResponse> request = new ArrayList<>();

    private LocalDateTime creationDate;

    private LocalDateTime completionDate;

    private String status;

    public SupplyRequestResponse(SupplyRequest request) {
        this.id = request.getId();
        this.factory = new FactoryResponse(request.getFactory());
        this.creationDate = request.getCreationDate();

        if(request.getCompletionDate() != null)
            this.completionDate = request.getCompletionDate();

        this.status = request.getStatus().toString();

        for (Supply supply : request.getRequestBasket().keySet()){
            this.request.add(new SupplyBasketResponse(supply, request.getRequestBasket().get(supply)));
        }
    }
}
