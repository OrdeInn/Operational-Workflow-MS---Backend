package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.EStatus;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Order;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.OrderService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.specifications.OrderSpec;
import com.thesis.Operational.Workflow.Management.and.Automation.System.utils.UncompletedOrdersExport;
import io.swagger.annotations.Api;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Api(tags = "Reports Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final OrderService orderService;

    public ReportController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/uncompletedOrders")
    public ResponseEntity getUncompletedOrdersReport() throws IOException {

        Specification<Order> orderSpec = OrderSpec.byStatus(EStatus.WAITING_FOR_GRAPHICS_DESIGNER)
                .or(OrderSpec.byStatus(EStatus.WAITING_FOR_PRODUCTION))
                .or(OrderSpec.byStatus(EStatus.WAITING_FOR_DELIVER))
                .or(OrderSpec.byStatus(EStatus.SHIPPING_TO_WAREHOUSE));

        List<Order> uncompletedOrders = orderService.findAll(orderSpec);
        UncompletedOrdersExport reportExport = new UncompletedOrdersExport();

        try{
            reportExport.export(uncompletedOrders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        String filename = "Order Export" + ".xlsx";

        headers.setContentDisposition(
                ContentDisposition.builder("attachment").filename(filename, Charset.forName("utf-8")).build());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ByteArrayResource contents = reportExport.base();

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }
}
