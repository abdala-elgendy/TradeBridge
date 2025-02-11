package com.global.demo.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.security.core.userdetails.User;

import java.sql.Date;

public class Favorite {
    private Long id;

    private Date dateSaved;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
