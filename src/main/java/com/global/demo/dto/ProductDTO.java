// src/main/java/com/global/demo/dto/ProductDTO.java
package com.global.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;


    private Integer totalSold;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.01", message = "Weight must be greater than 0")
    private BigDecimal weight;

    @NotBlank(message = "Weight unit is required")
    private String weightUnit;

    private Long supplierId;

    private List<ProductReviewDTO> reviews;
    private Double averageRating;
    private Integer totalReviews;

    // Thread-safe methods for updating stock and total sold
    public synchronized boolean updateStock(int quantity) {
        if (quantity > 0 && this.stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
            this.totalSold += quantity;
            return true;
        }
        return false;
    }

    public synchronized boolean addToStock(int quantity) {
        if (quantity > 0) {
            this.stockQuantity += quantity;
            return true;
        }
        return false;
    }
}