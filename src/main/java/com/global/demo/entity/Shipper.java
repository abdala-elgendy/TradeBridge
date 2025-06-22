package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@Table(name = "shippers")
@EqualsAndHashCode(callSuper = true)
public class Shipper extends BaseRoleEntity {

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "service_area")
    private String serviceArea;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "shippingRate")
    private double shippingRate;

    @Column(name = "locationNow")
    private String locationNow;

    @Column(name = "total_deliveries")
    private Integer totalDeliveries = 0;

    @Column(name = "is_available")
    private boolean available = true;

    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    public String getName() {
        return this.getUser().getName();
    }
}