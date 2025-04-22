package com.global.demo.service;

import com.global.demo.entity.*;
import com.global.demo.repository.OrderItemRepo;
import com.global.demo.repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepo productRepo;
    private final OrderItemRepo orderItemRepo;
    private final EmailService emailService;

    @Transactional
    public Order createOrder(Customer customer, List<OrderItem> items, String shipAddress, String shipCity) {
        Order order = Order.builder()
                .customer(customer)
                .shipAddress(shipAddress)
                .shipCity(shipCity)
                .status(OrderStatus.PENDING)
                .build();

        for (OrderItem item : items) {
            try {
                Product product = productRepo.findById(item.getProduct().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found"));

                // Verify stock and update it atomically
                if (!product.updateStock(item.getQuantity())) {
                    throw new IllegalStateException("Insufficient stock for product: " + product.getName());
                }

                item.setUnitPrice(product.getPrice());
                order.addOrderItem(item);
                
                productRepo.save(product);
            } catch (ObjectOptimisticLockingFailureException e) {
                // Another transaction has modified the product, retry logic could be implemented here
                throw new IllegalStateException("Product was modified by another transaction. Please retry.");
            }
        }

        order.setStatus(OrderStatus.CONFIRMED);
        return order;
    }

    @Transactional 
    public void cancelOrderitem(OrderItem orderItem){

    }

    @Transactional
    public void cancelOrder(Order order) {
        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel order that has been shipped or delivered");
        }

        // Return products to inventory
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.addToStock(item.getQuantity());
            productRepo.save(product);
        }
    

        order.setStatus(OrderStatus.CANCELLED);
    }
    
    /**
     * Updates the current location of an order and sends notification email to customer
     * 
     * @param order The order to update
     * @param location The current location of the order
     * @return The updated order
     */
    @Transactional
    public Order updateOrderLocation(Order order, String location) {
        order.setCurrentLocation(location);
        order.setLastLocationUpdate(LocalDateTime.now());
        
        // Send email notification to customer
        emailService.sendOrderLocationUpdate(order);
        
        return order;
    }
    
    /**
     * Updates the status of an order and sends notification email to customer
     * 
     * @param order The order to update
     * @param status The new status of the order
     * @return The updated order
     */
    @Transactional
    public Order updateOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        
        // Update shipping date if status is SHIPPED
        if (status == OrderStatus.SHIPPED) {
            order.setShippedDate(LocalDateTime.now());
        }
        
        // Send email notification to customer
        emailService.sendOrderLocationUpdate(order);
        
        return order;
    }
}