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
    
    /**
     * Finds all orders for a specific customer
     * 
     * @param customerId The ID of the customer
     * @return List of orders belonging to the customer
     */
    List<Order> findByCustomerId(Long customerId);
    
    /**
     * Finds all orders for a specific customer with the given status
     * 
     * @param customerId The ID of the customer
     * @param status The order status to filter by
     * @return List of orders belonging to the customer with the specified status
     */
    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);
    
    /**
     * Finds a specific order by ID and customer ID to ensure ownership
     * 
     * @param orderId The ID of the order
     * @param customerId The ID of the customer
     * @return Optional containing the order if it belongs to the customer
     */
    java.util.Optional<Order> findByOrderIdAndCustomerId(Long orderId, Long customerId);
} 