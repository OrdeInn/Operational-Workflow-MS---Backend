package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Category;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Supply;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.EditSupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.NewSupplyRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items.SupplyResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.items.CategoryService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.items.SupplyService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Supply Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/supply")
public class SupplyController {

    private final SupplyService supplyService;

    private final CategoryService categoryService;

    public SupplyController(SupplyService supplyService, CategoryService categoryService) {
        this.supplyService = supplyService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<Page<SupplyResponse>> getSupplyPage(@RequestParam(name = "page") int page,
                                                              @RequestParam(name = "size") int size,
                                                              @RequestParam(name = "sort") String sort){

        Specification<Supply> spec = Specification.where(null);

        Page<Supply> supplyPage = supplyService.findAll(spec, page, size, sort);

        List<SupplyResponse> responseList = new ArrayList<>();
        supplyPage.forEach(supply -> {
            responseList.add(new SupplyResponse(supply));
        });

        Page<SupplyResponse> responsePage = new PageImpl<>(responseList, supplyPage.getPageable(), supplyPage.getTotalElements());

        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("")
    public ResponseEntity<SupplyResponse> createNewSupply(@RequestBody NewSupplyRequest request){

        Category category = categoryService.findById(request.getCategoryId());
        Supply supply = supplyService.createNewSupply(request, category);

        return ResponseEntity.ok(new SupplyResponse(supply));
    }

    @PatchMapping("")
    public ResponseEntity<SupplyResponse> editSupply(@RequestBody EditSupplyRequest request){

        Category category = categoryService.findById(request.getCategoryId());
        Supply supply = supplyService.editSupply(request, category);

        return ResponseEntity.ok(new SupplyResponse(supply));
    }
}
