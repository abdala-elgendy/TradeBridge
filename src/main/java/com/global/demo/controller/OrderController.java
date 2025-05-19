package com.global.demo.controller;

import com.global.demo.dto.OrderRequest;
import com.global.demo.entity.Customer;
import com.global.demo.entity.Order;
import com.global.demo.entity.OrderItem;
import com.global.demo.entity.Product;
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

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(
            @AuthenticationPrincipal Customer customer,
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