// src/main/java/com/global/demo/entity/OrderItem.java
package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items", indexes = {
    @Index(name = "idx_orderitem_order", columnList = "order_id"),
    @Index(name = "idx_orderitem_product", columnList = "product_id"),
    @Index(name = "idx_orderitem_order_product", columnList = "order_id, product_id")
})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;

    @Version
    private Long version;

    @PrePersist
    @PreUpdate
    protected void validateQuantity() {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}