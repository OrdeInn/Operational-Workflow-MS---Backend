package com.thesis.Operational.Workflow.Management.and.Automation.System.controllers.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Category;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.EditProductRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.NewProductRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.response.items.ProductResponse;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.items.CategoryService;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.items.ProductService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Product Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductResponse>> getProductPage(@RequestParam(name = "page") int page,
                                                                @RequestParam(name = "size") int size,
                                                                @RequestParam(name = "sort") String sort){

        Specification<Product> spec = Specification.where(null);

        Page<Product> productPage = productService.findAll(spec, page, size, sort);

        List<ProductResponse> responseList = new ArrayList<>();
        productPage.forEach(product -> {
            responseList.add(new ProductResponse(product));
        });

        Page<ProductResponse> responsePage = new PageImpl<>(responseList, productPage.getPageable(), productPage.getTotalElements());

        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("")
    public ResponseEntity<ProductResponse> createNewProduct(@RequestBody NewProductRequest request){

        Category category = categoryService.findById(request.getCategoryId());
        Product product = productService.createNewProduct(request, category);

        return ResponseEntity.ok(new ProductResponse(product));
    }

    @PatchMapping("")
    public ResponseEntity<ProductResponse> editProduct(@RequestBody EditProductRequest request){

        Category category = categoryService.findById(request.getCategoryId());
        Product product = productService.editProduct(request, category);

        return ResponseEntity.ok(new ProductResponse(product));
    }
}
