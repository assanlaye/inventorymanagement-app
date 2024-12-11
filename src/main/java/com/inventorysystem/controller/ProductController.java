package com.inventorysystem.controller;


import com.inventorysystem.entity.Product;
import com.inventorysystem.respository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    /**
     * Creates a new product
     * @param product The product data from the request body.
     * @return The saved Product object
     */
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        // Save the product
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    /** * Retrieves a product by ID. * @param id The ID of the product. * @return A ResponseEntity containing the Product object. */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        return ResponseEntity.ok(product);
    } /**
     * Updates an existing product.
     *  @param id The ID of the product to update.
     * @param productDetails The updated product data.
     * @return A ResponseEntity containing the updated Product object.
     */

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));

        // Update the product's details using Lombok-generated setters
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setQuantity(productDetails.getQuantity());
        product.setPrice(productDetails.getPrice());
        // Save the updated product
        Product updatedProduct = productRepository.save(product);

        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes a product by ID.
     * @param id The ID of the product to delete.
     * @return A ResponseEntity with HTTP status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));

        productRepository.delete(product);

        return ResponseEntity.noContent().build();
    }
}
