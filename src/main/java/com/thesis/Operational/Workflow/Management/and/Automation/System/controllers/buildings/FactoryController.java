package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Factory;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.EditFactoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.NewFactoryRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.buildings.FactoryResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.UserService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings.FactoryService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Api(tags = "Factory Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/factory")
public class FactoryController {

    private final FactoryService factoryService;
    private final UserService userService;

    public FactoryController(FactoryService factoryService, UserService userService) {
        this.factoryService = factoryService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Page<FactoryResponse>> getFactoryPage(@RequestParam(name = "page") int page,
                                                               @RequestParam(name = "size") int size,
                                                               @RequestParam(name = "sort") String sort){


        Specification<Factory> spec = Specification.where(null);

        Page<Factory> factoryPage = factoryService.findAll(spec, page, size, sort);

        List<FactoryResponse> responseList = new ArrayList<>();
        factoryPage.forEach(factory -> {
            responseList.add(new FactoryResponse(factory));
        });
        Page<FactoryResponse> responsePage = new PageImpl<>(responseList, factoryPage.getPageable(), factoryPage.getTotalElements());
        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("")
    public ResponseEntity<FactoryResponse> createNewFactory(@RequestBody NewFactoryRequest request){

        Factory factory = factoryService.createNewFactory(request);
        return ResponseEntity.ok(new FactoryResponse(factory));
    }

    @PatchMapping("")
    public ResponseEntity<FactoryResponse> editFactory(@RequestBody EditFactoryRequest request){

        Set<User> employees = new HashSet<>();
        request.getEmployees().forEach(userId -> {
            User user = userService.findById(userId);
            if(user != null)
                employees.add(user);
        });

        Factory factory = factoryService.editFactory(request, employees);

        for (User employee : employees){
            employee.setBuilding(factory);
        }
        userService.saveAll(employees);

        return ResponseEntity.ok(new FactoryResponse(factory));
    }
}
