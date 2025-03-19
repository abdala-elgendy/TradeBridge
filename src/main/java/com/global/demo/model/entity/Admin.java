package com.global.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;

@Data
@Entity
@Table(name = "admins")
@EqualsAndHashCode(callSuper = true)
public class Admin extends BaseRoleEntity {
    
    @Column(name = "department")
    private String department;
    
    @Column(name = "access_level")
    private String accessLevel;
    
    @Column(name = "can_manage_users")
    private boolean canManageUsers = false;
    
    @Column(name = "can_manage_products")
    private boolean canManageProducts = false;
    
    @Column(name = "can_manage_orders")
    private boolean canManageOrders = false;
    
    @Column(name = "can_manage_suppliers")
    private boolean canManageSuppliers = false;
    
    @Column(name = "can_manage_shippers")
    private boolean canManageShippers = false;
    
    @Column(name = "can_view_reports")
    private boolean canViewReports = false;
} 