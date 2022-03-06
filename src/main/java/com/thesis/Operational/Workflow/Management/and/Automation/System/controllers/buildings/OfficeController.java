package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers.buildings;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.User;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.buildings.Office;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.EditOfficeRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.buildings.NewOfficeRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.buildings.OfficeResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.UserService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.buildings.OfficeService;
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

@Api(tags = "Office Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/office")
public class OfficeController {

    private final OfficeService officeService;
    private final UserService userService;

    public OfficeController(OfficeService officeService, UserService userService) {
        this.officeService = officeService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Page<OfficeResponse>> getOfficePage(@RequestParam(name = "page") int page,
                                                              @RequestParam(name = "size") int size,
                                                              @RequestParam(name = "sort") String sort){


        Specification<Office> spec = Specification.where(null);

        Page<Office> officePage = officeService.findAll(spec, page, size, sort);

        List<OfficeResponse> responseList = new ArrayList<>();
        officePage.forEach(office -> {
            responseList.add(new OfficeResponse(office));
        });
        System.out.println(responseList.size());
        Page<OfficeResponse> responsePage = new PageImpl<>(responseList, officePage.getPageable(), officePage.getTotalElements());
        return ResponseEntity.ok(responsePage);
    }

     @PostMapping("")
    public ResponseEntity<OfficeResponse> createNewOffice(@RequestBody NewOfficeRequest request){

        Office office = officeService.createNewOffice(request);
        return ResponseEntity.ok(new OfficeResponse(office));
    }

    @PatchMapping("")
    public ResponseEntity<OfficeResponse> editOffice(@RequestBody EditOfficeRequest request){

        Set<User> employees = new HashSet<>();
        request.getEmployees().forEach(userId -> {
            User user = userService.findById(userId);
            if(user != null)
                employees.add(user);
        });

        Office office = officeService.editOffice(request, employees);

        return ResponseEntity.ok(new OfficeResponse(office));
    }


}
