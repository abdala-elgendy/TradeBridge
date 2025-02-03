package com.global.demo.entity.shipping;

import jakarta.persistence.*;

@Table(name="shipping_address")
public class ShippingAddress {  // the working area of the shipper


    @Column(name = "city")
    private String city;

    @Column(name = "government")
    private String government;

    @ManyToOne
    @JoinColumn(name = "shipperId")
    private Shipper shipper;
}
