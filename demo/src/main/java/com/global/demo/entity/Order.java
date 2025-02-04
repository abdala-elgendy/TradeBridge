package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Setter
@Getter
@Table(name="orders")
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



    // Getters, setters, and constructors
}

