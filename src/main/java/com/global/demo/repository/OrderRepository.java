package com.global.demo.repository;

import com.global.demo.entity.Order;
import com.global.demo.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Finds all orders with the specified status that were created between the given dates
     * 
     * @param status The order status to search for
     * @param startDate The start of the date range (inclusive)
     * @param endDate The end of the date range (inclusive)
     * @return List of orders matching the criteria
     */
    List<Order> findByStatusAndOrderDateBetween(
        OrderStatus status,
        LocalDateTime startDate,
        LocalDateTime endDate
    );
} 