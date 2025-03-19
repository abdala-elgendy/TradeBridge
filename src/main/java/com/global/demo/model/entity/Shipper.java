package com.global.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;

@Data
@Entity
@Table(name = "shippers")
@EqualsAndHashCode(callSuper = true)
public class Shipper extends BaseRoleEntity {
    
    @Column(name = "company_name")
    private String companyName;
    
    @Column(name = "business_registration_number")
    private String businessRegistrationNumber;
    
    @Column(name = "tax_id")
    private String taxId;
    
    @Column(name = "service_area")
    private String serviceArea;
    
    @Column(name = "vehicle_type")
    private String vehicleType;
    
    
    @Column(name = "rating")
    private Double rating = 0.0;
    
    @Column(name = "total_deliveries")
    private Integer totalDeliveries = 0;
    
    @Column(name = "is_available")
    private boolean available = true;
} 