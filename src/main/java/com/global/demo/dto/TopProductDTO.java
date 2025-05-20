package com.global.demo.dto;

import java.math.BigDecimal;

public class TopProductDTO {

    private BigDecimal price;
    private Integer stockQuantity;
    private int rate;
    private String name;
    private String description;

    // Constructor matching the query
    public TopProductDTO(BigDecimal price, Integer stockQuantity, int rate, String name, String description) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.rate = rate;
        this.name = name;
        this.description = description;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}