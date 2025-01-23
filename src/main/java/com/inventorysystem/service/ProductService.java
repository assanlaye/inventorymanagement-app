package com.inventorysystem.service;

import com.inventorysystem.entity.Product;
import com.inventorysystem.repository.ProductRepository; // Updated to use ProductRepo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;  // Updated to use ProductRepo

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();  // Updated to use ProductRepo
    }

    // Add a new product
    public void addProduct(Product product) {
        productRepository.save(product);  // Updated to use ProductRepo
    }

    // Get product by ID
    public Optional<Product> getProductById(String id) {
        // Assuming ID is a Long
        return productRepository.findById(Long.parseLong(id));  // Updated to use ProductRepo
    }

    // Delete product by ID
    public boolean deleteProductById(String id) {
        Optional<Product> product = productRepository.findById(Long.parseLong(id));  // Updated to use ProductRepo
        if (product.isPresent()) {
            productRepository.delete(product.get());  // Updated to use ProductRepo
            return true;
        }
        return false;
    }
}
