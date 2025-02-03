package com.global.demo.entity.shipping;

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


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "Phone")
    private String phone;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "shippingCount")
    private int shippingCount;

    @Column(name = "shippingRate")
    private double shippingRate;

    @ManyToOne
    @JoinColumn(name = "shippingCompanyId")
    private ShippingCompany shippingCompany;

    @OneToMany(
            mappedBy = "shipper"
            ,cascade = CascadeType.ALL)
    private List<ShippingAddress> shippingAddresses;
}
