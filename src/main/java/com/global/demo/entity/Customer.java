package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Data
@Entity
@Table(name = "customers", indexes = {
    @Index(name = "idx_customer_user", columnList = "user_id"),
    @Index(name = "idx_customer_active", columnList = "is_active"),
    @Index(name = "idx_customer_created_at", columnList = "created_at"),
    @Index(name = "idx_customer_loyalty_points", columnList = "loyalty_points")
})
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseRoleEntity {

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "preferred_payment_method")
    private String preferredPaymentMethod;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints = 0;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 50)  // Optimize batch loading for orders
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 50)  // Optimize batch loading for favorites
    private List<Favorite> favorites;

    public String getName() {
        return this.getUser().getName();
    }
    public String getEmail() {
        return this.getUser().getEmail();
    }
}