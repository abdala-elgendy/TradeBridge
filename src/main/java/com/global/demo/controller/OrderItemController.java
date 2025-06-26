// src/main/java/com/global/demo/controller/OrderItemController.java
package com.global.demo.controller;

import com.global.demo.entity.OrderItem;
import com.global.demo.entity.User;
import com.global.demo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/add")
    public OrderItem addOrderItem(@AuthenticationPrincipal User user,@RequestBody OrderItem orderItem
        ) {
        return orderItemService.addOrderItem(orderItem);
    }

    @GetMapping("/allOrderItems")
    public List<OrderItem> getAllOrderItems(@AuthenticationPrincipal User user) {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return orderItemService.getOrderItemById(id);
    }

 

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@AuthenticationPrincipal User user, @PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }
}