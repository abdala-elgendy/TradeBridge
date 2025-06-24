package com.global.demo.controller;

import com.global.demo.dto.OrderRequest;
import com.global.demo.entity.Customer;
import com.global.demo.entity.Order;
import com.global.demo.entity.OrderItem;
import com.global.demo.entity.OrderStatus;
import com.global.demo.entity.Product;
import com.global.demo.entity.User;
import com.global.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(
            @AuthenticationPrincipal Customer customer,
            @Valid @RequestBody OrderRequest orderRequest
    ) {
        try {
            List<OrderItem> orderItems = orderRequest.getItems().stream()
                    .map(item -> OrderItem.builder()
                            .product(Product.builder().id(item.getProductId()).build())
                            .quantity(item.getQuantity())
                            .build())
                    .collect(Collectors.toList());

            Order order = orderService.createOrder(
                    customer,
                    orderItems,
                    orderRequest.getShipAddress(),
                    orderRequest.getShipCity()
            );

            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all orders for the authenticated customer
     */
    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal User user) {
        Customer customer = user.getCustomer();
        if (customer == null) {
            return ResponseEntity.status(403).build();
        }
        List<Order> orders = orderService.getOrdersByCustomerId(customer.getId());
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders for the authenticated customer with specific status
     */
    @GetMapping("/my-orders/status/{status}")
    public ResponseEntity<List<Order>> getMyOrdersByStatus(
            @AuthenticationPrincipal User user,
            @PathVariable OrderStatus status
    ) {
        Customer customer = user.getCustomer();
        if (customer == null) {
            return ResponseEntity.status(403).build();
        }
        List<Order> orders = orderService.getOrdersByCustomerIdAndStatus(customer.getId(), status);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get a specific order by ID, ensuring it belongs to the authenticated customer
     */
    @GetMapping("/my-orders/{orderId}")
    public ResponseEntity<Order> getMyOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId
    ) {
        Customer customer = user.getCustomer();
        if (customer == null) {
            return ResponseEntity.status(403).build();
        }
        try {
            Order order = orderService.getOrderByIdAndCustomerId(orderId, customer.getId());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cancel an order, ensuring it belongs to the authenticated customer
     */
    @PostMapping("/my-orders/{orderId}/cancel")
    public ResponseEntity<?> cancelMyOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId
    ) {
        Customer customer = user.getCustomer();
        if (customer == null) {
            return ResponseEntity.status(403).build();
        }
        try {
            Order order = orderService.getOrderByIdAndCustomerId(orderId, customer.getId());
            orderService.cancelOrder(order);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Legacy endpoint for backward compatibility (admin use only)
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long orderId
    ) {
        try {
            orderService.cancelOrder(Order.builder().orderId(orderId).build());
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}