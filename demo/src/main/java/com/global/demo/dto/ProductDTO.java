// src/main/java/com/global/demo/dto/ProductDTO.java
package com.global.demo.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int unitPrice;
    private int quantityInStock;
    private int discount;
    private Long supplierId;

}