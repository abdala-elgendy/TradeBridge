package com.global.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;

@Data
@Entity
@Table(name = "suppliers")
@EqualsAndHashCode(callSuper = true)
public class Supplier extends BaseRoleEntity {
    
    @Column(name = "company_name")
    private String companyName;
    
    @Column(name = "business_registration_number")
    private String businessRegistrationNumber;
    
    @Column(name = "tax_id")
    private String taxId;
    
    @Column(name = "warehouse_address")
    private String warehouseAddress;
    
    @Column(name = "contact_person")
    private String contactPerson;
    
    @Column(name = "contact_phone")
    private String contactPhone;
    
    @Column(name = "rating")
    private Double rating = 0.0;
    
    @Column(name = "total_products")
    private Integer totalProducts = 0;
} 