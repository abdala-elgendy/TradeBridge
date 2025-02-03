package com.global.demo.entity.shipping;

import jakarta.persistence.*;

import java.util.List;

@Table(name="shipping_company")
public class ShippingCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String companyName;
    private String location;
    private String phone;
    private String email;
    private String shippingAddress;
    private String shippingCity;

    @OneToMany(
            mappedBy = "shippingCompany",
            cascade = CascadeType.ALL
    )
    private List<Shipper> shippers;
}
