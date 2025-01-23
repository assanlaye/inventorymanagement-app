package com.inventorysystem.repository;

import com.inventorysystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository interface for managing Product entities in the database.
 * Extends JpaRepository to provide CRUD operations and additional query capabilities.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
