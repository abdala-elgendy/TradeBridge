package com.global.demo.entity;

import com.global.demo.entity.Customer;
import com.global.demo.entity.Shipper;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private LocalDateTime requiredDate;

    @Column
    private LocalDateTime shippedDate;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false)
    private String shipAddress;
    @Column(nullable = false)
    private String shipCity;
    
    // Location tracking information
    @Column
    private String currentLocation;
    @Column
    private LocalDateTime lastLocationUpdate;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
        if (status == null) {
            status = OrderStatus.PENDING;
        }
    }

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
        recalculateTotalAmount();
    }

    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
        recalculateTotalAmount();
    }

    private void recalculateTotalAmount() {
        this.totalAmount = orderItems.stream()
                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
