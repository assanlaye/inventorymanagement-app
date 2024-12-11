package com.inventorysystem.respository;

import com.inventorysystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on Product entities.
 * Extends JpaRepository to inherit common data operations.
 */
public interface ProductRepository extends JpaRepository<Product, Long > {
}
