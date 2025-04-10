package com.inventorysystem.entity;

import com.inventorysystem.dto.OrderDto;
import com.inventorysystem.enums.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;

    private LocalDate deliveryDate;

    private double price;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getOrderStatus(OrderStatus pending) {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            this.price = product.getPrice(); // Setting order price from Product entity
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderDto getOrderDto(){
        OrderDto orderDto = new OrderDto();

        orderDto.setId(id);
        orderDto.setPrice(price);
        orderDto.setOrderDate(orderDate);
        orderDto.setDeliveryDate(deliveryDate);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setQuantity(quantity);

        orderDto.setUserId(user.getId());
        orderDto.setUsername(user.getFirstName());

        orderDto.setProductId(product.getId());
        orderDto.setProductName(product.getProductName());
        orderDto.setProductCategory(product.getProductCategory());


        return orderDto;
    }


}
