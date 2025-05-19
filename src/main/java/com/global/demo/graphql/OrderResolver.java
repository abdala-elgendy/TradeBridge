package com.global.demo.graphql;

import com.global.demo.entity.Order;
import com.global.demo.entity.OrderItem;
import com.global.demo.entity.OrderStatus;
import com.global.demo.entity.Product;
import com.global.demo.service.OrderService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final OrderService orderService;

    public List<OrderItem> orders() {
        return orderService.getAllOrders();
    }

    public OrderItem order(Long id) {
        return orderService.getOrderById(id);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    public Order createOrder(OrderInput input) {
        return orderService.createOrder(
            input.getItems().stream()
                .map(item -> OrderItem.builder()
                    .product(Product.builder().id(item.getProductId()).build())
                    .quantity(item.getQuantity())
                    .build())
                .collect(Collectors.toList()),
            input.getShipAddress(),
            input.getShipCity()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SHIPPER')")
    public OrderItem updateOrderStatus(Long id, OrderStatus status) {
        OrderItem order = orderService.getOrderById(id);
        return orderService.updateOrderStatus(order, status);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    public Order cancelOrder(Long id) {
        Order order = orderService.getOrderById(id);
        orderService.cancelOrder(order);
        return order;
    }
} 