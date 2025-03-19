package com.global.demo.model.dto;

import com.global.demo.model.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String phoneNumber;

    private Role role;

    // Customer specific fields
    private String shippingAddress;
    private String billingAddress;
    private String preferredPaymentMethod;

    // Supplier specific fields
    private String companyName;
    private String businessRegistrationNumber;
    private String taxId;
    private String warehouseAddress;
    private String contactPerson;
    private String contactPhone;

    // Shipper specific fields
    private String serviceArea;
    private String vehicleType;
    private String licenseNumber;

    // Admin specific fields
    private String department;
    private String accessLevel;
} 