package com.global.demo.service;

import com.global.demo.entity.*;
import com.global.demo.repository.OrderItemRepo;
import com.global.demo.repository.OrderRepository;
import com.global.demo.repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepo productRepo;
    private final OrderItemRepo orderItemRepo;
    private final EmailService emailService;
    private final OrderRepository orderRepo;
    private static final int MAX_RETRIES = 3;

    @Transactional
    @Retryable(
        value = { ObjectOptimisticLockingFailureException.class },
        maxAttempts = MAX_RETRIES,
        backoff = @Backoff(delay = 100, multiplier = 2)
    )
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
                // Retry will be handled by @Retryable
                throw e;
            } catch (Exception e) {
                // If any other error occurs, rollback the entire transaction
                throw new IllegalStateException("Failed to process order: " + e.getMessage());
            }
        }

        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepo.save(order);
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
        orderRepo.save(order);
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
        
        return orderRepo.save(order);
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
        
        return orderRepo.save(order);
    }

    /**
     * Get all orders for a specific customer
     * 
     * @param customerId The ID of the customer
     * @return List of orders belonging to the customer
     */
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepo.findByCustomerId(customerId);
    }
    
    /**
     * Get orders for a specific customer with the given status
     * 
     * @param customerId The ID of the customer
     * @param status The order status to filter by
     * @return List of orders belonging to the customer with the specified status
     */
    public List<Order> getOrdersByCustomerIdAndStatus(Long customerId, OrderStatus status) {
        return orderRepo.findByCustomerIdAndStatus(customerId, status);
    }
    
    /**
     * Get a specific order by ID, ensuring it belongs to the customer
     * 
     * @param orderId The ID of the order
     * @param customerId The ID of the customer
     * @return The order if it belongs to the customer
     * @throws EntityNotFoundException if the order doesn't exist or doesn't belong to the customer
     */
    public Order getOrderByIdAndCustomerId(Long orderId, Long customerId) {
        return orderRepo.findByOrderIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found or access denied"));
    }

    // Legacy methods for backward compatibility (admin use only)
    public List<OrderItem> getAllOrders() {
        return orderItemRepo.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));
    }

    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}