package com.inventorysystem.controller.customer;

import com.inventorysystem.service.customer.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("customerProductController")
@RequestMapping("/api/customer")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{pageNumber}")
    public ResponseEntity<?> getAvailableProducts(@PathVariable int pageNumber){
        return  ResponseEntity.ok(productService.getAvailableProducts(pageNumber));
    }
}
