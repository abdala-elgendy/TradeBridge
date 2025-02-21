package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name="shippers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String name;


    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "Phone",nullable = false)
    private String phone;

    @Column(name = "companyName")
    private String companyName;


    @Column(name = "shippingRate")
    private double shippingRate;

    @Column(name="locationNow")
    private String locationNow;

     @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

}
