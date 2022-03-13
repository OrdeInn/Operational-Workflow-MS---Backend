package com.thesis.Operational.Workflow.Management.and.Automation.System.services.items;

import com.thesis.Operational.Workflow.Management.and.Automation.System.exceptions.ResourceNotFoundException;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Category;
import com.thesis.Operational.Workflow.Management.and.Automation.System.models.items.Product;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.EditProductRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.payloads.request.items.NewProductRequest;
import com.thesis.Operational.Workflow.Management.and.Automation.System.repositories.items.ProductRepository;
import com.thesis.Operational.Workflow.Management.and.Automation.System.services.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product, Long> {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        super(productRepository, "Product");
        this.productRepository = productRepository;
    }

    public Product createNewProduct(NewProductRequest request, Category category){

        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(category);
        product.setPrice(request.getPrice());

        return productRepository.save(product);
    }

    public Product editProduct(EditProductRequest request, Category category){

        Product product = productRepository.findById(request.getId()).orElse(null);

        if(product != null){
            if(request.getName() != null)
                product.setName(request.getName());

            if(request.getPrice() > 0)
                product.setPrice(request.getPrice());

            if(category != null)
                product.setCategory(category);

            return productRepository.save(product);
        }else {
            throw new ResourceNotFoundException("Product", request.getId());
        }
    }
}
