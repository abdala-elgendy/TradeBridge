package com.global.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    @jakarta.persistence.Id
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


    @Column(name = "rating")
    private double rating;

    @Column(name="locationNow")
    private String locationNow;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
