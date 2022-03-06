package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Warehouse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.EditWarehouseRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.NewWarehouseRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.buildings.WarehouseResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.UserService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings.WarehouseService;
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

@Api(tags = "Warehouse Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final UserService userService;

    public WarehouseController(WarehouseService warehouseService, UserService userService) {
        this.warehouseService = warehouseService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Page<WarehouseResponse>> getWarehousePage(@RequestParam(name = "page") int page,
                                                                    @RequestParam(name = "size") int size,
                                                                    @RequestParam(name = "sort") String sort){


        Specification<Warehouse> spec = Specification.where(null);

        Page<Warehouse> warehousePage = warehouseService.findAll(spec, page, size, sort);

        List<WarehouseResponse> responseList = new ArrayList<>();
        warehousePage.forEach(warehouse -> {
            responseList.add(new WarehouseResponse(warehouse));
        });
        Page<WarehouseResponse> responsePage = new PageImpl<>(responseList, warehousePage.getPageable(), warehousePage.getTotalElements());
        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("")
    public ResponseEntity<WarehouseResponse> createNewWarehouse(@RequestBody NewWarehouseRequest request){

        Warehouse warehouse = warehouseService.createNewWarehouse(request);
        return ResponseEntity.ok(new WarehouseResponse(warehouse));
    }

    @PatchMapping("")
    public ResponseEntity<WarehouseResponse> editWarehouse(@RequestBody EditWarehouseRequest request){

        Set<User> employees = new HashSet<>();
        request.getEmployees().forEach(userId -> {
            User user = userService.findById(userId);
            if(user != null)
                employees.add(user);
        });

        Warehouse warehouse = warehouseService.editWarehouse(request, employees);

        for (User employee : employees){
            employee.setBuilding(warehouse);
        }
        userService.saveAll(employees);

        return ResponseEntity.ok(new WarehouseResponse(warehouse));
    }
}
