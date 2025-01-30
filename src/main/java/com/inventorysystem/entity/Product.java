package com.inventorysystem.entity;

import com.inventorysystem.dto.ProductDto;
import jakarta.persistence.*;
@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productName", nullable = false)
    private String productName;

    @Column(name = "productCategory", nullable = false)
    private String productCategory;


    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "available", nullable = false)
    private boolean available;


    public Product(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductDto getProductDto(){
        ProductDto productDto = new ProductDto();

        productDto.setId(id);
        productDto.setProductName(productName);
        productDto.setProductCategory(productCategory);
        productDto.setPrice(price);
        productDto.setQuantity(quantity);
        productDto.setAvailable(available);

        return productDto;

    }
}
