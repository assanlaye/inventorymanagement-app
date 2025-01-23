package com.inventorysystem.controller;

import com.inventorysystem.entity.Product;
import com.inventorysystem.exception.ProductNotFoundException;
import com.inventorysystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")  // Define a base URL for product-related operations
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();  // Returns a list of products in JSON format
    }

    // Create a new product
    @PostMapping("/")
    public ResponseEntity<String> newProduct(@RequestBody Product product) {
        productService.addProduct(product);  // No need to manually generate ID
        return ResponseEntity.ok("Product added successfully!");  // Success message
    }

    // Search for a product by ID
    @GetMapping("/searchProduct/{id}")
    public ResponseEntity<Object> searchProduct(@PathVariable String id) {
        Optional<Product> foundProduct = productService.getProductById(id);

        if (foundProduct.isPresent()) {
            return ResponseEntity.ok(foundProduct.get());  // Return the product if found
        } else {
            throw new ProductNotFoundException("Product with id " + id + " not found");  // Return Product with id not found
        }
    }

    // Delete a product by ID
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        boolean deleteSuccess = productService.deleteProductById(id);

        if (deleteSuccess) {
            return ResponseEntity.ok("Product deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Product not found!");
        }
    }

    // Get product analysis (list of all products for analysis)
    @GetMapping("/productAnalysis")
    public List<Product> productAnalysis() {
        return productService.getAllProducts();
    }
}
