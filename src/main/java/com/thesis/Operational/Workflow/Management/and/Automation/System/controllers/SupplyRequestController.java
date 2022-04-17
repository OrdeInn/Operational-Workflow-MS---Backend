package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.SupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Warehouse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.NewSupplyRequestPayload;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.SupplyBasketRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.SupplyRequestResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.SupplyRequestRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.SupplyRequestService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.UserService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings.FactoryService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Factory;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings.WarehouseService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.items.SupplyService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.specifications.SupplyRequestSpec;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Api(tags = "Supply Request Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/supplyRequest")
public class SupplyRequestController {

    private final SupplyRequestService supplyRequestService;
    private final FactoryService factoryService;
    private final WarehouseService warehouseService;
    private final SupplyService supplyService;
    private final UserService userService;

    public SupplyRequestController(SupplyRequestService supplyRequestService, FactoryService factoryService,
                                   SupplyService supplyService, UserService userService, WarehouseService warehouseService) {
        this.supplyRequestService = supplyRequestService;
        this.factoryService = factoryService;
        this.supplyService = supplyService;
        this.userService = userService;
        this.warehouseService = warehouseService;
    }

    @GetMapping("")
    public ResponseEntity<Page<SupplyRequestResponse>> getSupplyRequests(@RequestParam(name = "page") int page,
                                                                         @RequestParam(name = "size") int size,
                                                                         @RequestParam(name = "sort") String sort, Principal principal){

        User user = userService.findByEMail(principal.getName());
        Warehouse warehouse = warehouseService.findById(user.getBuilding().getId());
        Set<Factory> responsibleFactories = warehouse.getResponsibleFactories();
        Specification<SupplyRequest> supplyRequestSpec = Specification.where(null);

        for(Factory factory : responsibleFactories){
            supplyRequestSpec = supplyRequestSpec.or(SupplyRequestSpec.byFactory(factory.getId()));
        }

        Page<SupplyRequest> supplyRequestPage = supplyRequestService.findAll(supplyRequestSpec, page, size, sort);
        List<SupplyRequestResponse> responseList = new ArrayList<>();

        supplyRequestPage.forEach(request -> {
            responseList.add(new SupplyRequestResponse(request));
        });
        Page<SupplyRequestResponse> responsePage =
                new PageImpl<>(responseList, supplyRequestPage.getPageable(), supplyRequestPage.getTotalElements());

        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("")
    public ResponseEntity crateNewRequest(@RequestBody NewSupplyRequestPayload requestPayload){

        Factory factory = factoryService.findById(requestPayload.getFactoryId());
        Map<Supply, Integer> requestBasket = new HashMap<>();

        for(SupplyBasketRequest basketRequest : requestPayload.getRequest()){
            Supply supply = supplyService.findById(basketRequest.getSupplyId());
            if (supply != null){
                requestBasket.put(supply, basketRequest.getQuantity());
            } else {
                return ResponseEntity.badRequest().body("Supply doesn't exist with ID " + basketRequest.getSupplyId());
            }
        }

        SupplyRequest request = supplyRequestService.createSupplyRequest(factory, requestPayload, requestBasket);

        if(request == null){
            return ResponseEntity.badRequest().body("Factory doesn't exist with ID " + requestPayload.getFactoryId());
        }

        return ResponseEntity.ok(new SupplyRequestResponse(request));
    }
}
