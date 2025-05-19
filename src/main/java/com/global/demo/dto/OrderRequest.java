package com.global.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotEmpty(message = "Order items are required")
    private List<OrderItemRequest> items;

    @NotNull(message = "Shipping address is required")
    private String shipAddress;

    @NotNull(message = "Shipping city is required")
    private String shipCity;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {
        @NotNull(message = "Product ID is required")
        private Long productId;

        @NotNull(message = "Quantity is required")
        private Integer quantity;
    }
}