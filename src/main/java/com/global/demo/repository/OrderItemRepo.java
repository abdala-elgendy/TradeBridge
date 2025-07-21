package com.global.demo.repository;

import com.global.demo.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findById(Long id);

}
