package com.global.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="product_comments")
@Entity
@Getter
@Setter

public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="comment")
    private String comment;


    @Column(name="rating")
 private  int rating;


    @ManyToOne
    @JoinColumn(name = "product_id")
private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
