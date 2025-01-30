package com.inventorysystem.controller.admin;

import com.inventorysystem.dto.ProductDto;

import com.inventorysystem.service.admin.ProductService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("adminProductController")
@RequestMapping("/api/admin")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<?> postProduct(@RequestBody ProductDto productDto){
        boolean success = productService.postProduct(productDto);
        if (success){
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/products/{pageNumber}")
    public ResponseEntity<?> getAllProducts(@PathVariable int pageNumber){
        return  ResponseEntity.ok(productService.getAllProducts(pageNumber));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try {
             return ResponseEntity.ok(productService.getProductById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestBody ProductDto productDto){
        boolean success = productService.updateProduct(id, productDto);
        if (success){
            return  ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return  ResponseEntity.ok(null);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
