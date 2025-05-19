package com.global.demo.graphql;

import lombok.Data;
import java.util.List;

@Data
public class OrderInput {
    private List<OrderItemInput> items;
    private String shipAddress;
    private String shipCity;
}

@Data
class OrderItemInput {
    private Long productId;
    private Integer quantity;
} 