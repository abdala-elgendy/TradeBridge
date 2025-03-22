package com.global.demo.entity;

import com.global.demo.entity.Customer;
import com.global.demo.entity.Shipper;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shippedDate;

    private String shipAddress;
    private String shipCity;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;

}
