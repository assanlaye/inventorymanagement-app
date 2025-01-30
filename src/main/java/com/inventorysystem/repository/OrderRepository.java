package com.inventorysystem.repository;

import com.inventorysystem.entity.Order;
import com.inventorysystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

        Page<Order> findAllByUserId(Pageable pageable, Long userId);

    Long user(User user);
}
