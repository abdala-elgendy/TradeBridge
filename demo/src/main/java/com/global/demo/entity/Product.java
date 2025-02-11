package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name="products")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private Long Id;
    private String name;
    private int unitPrice;
    private int quantityInStock;
    private int discount;


    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier supplier;


    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

}
