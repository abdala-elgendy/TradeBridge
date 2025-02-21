// src/main/java/com/global/demo/service/OrderItemService.java
package com.global.demo.service;

import com.global.demo.entity.OrderItem;
import com.global.demo.repository.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepo orderItemRepository;

    public OrderItem addOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        return orderItem.orElse(null);
    }

    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        if (orderItemRepository.existsById(id)) {
            orderItem.setId(id);
            return orderItemRepository.save(orderItem);
        }
        return null;
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}