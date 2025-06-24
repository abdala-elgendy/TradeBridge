package com.global.demo.service;

import com.global.demo.entity.*;
import com.global.demo.dto.AuthResponse;
import com.global.demo.dto.LoginRequest;
import com.global.demo.dto.RegisterRequest;
import com.global.demo.repository.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final ShipperRepository shipperRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Transactional
    public AuthResponse register(RegisterRequest request) throws Exception {
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder().success(false).message("Email already registered").build();
        }

        if (userRepository.existsByNationalId(request.getNationalId())) {
            return AuthResponse.builder().success(false).message("National ID already registered").build();
        }

        // Create base user
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .nationalId(request.getNationalId()).phoneNumber(request.getPhoneNumber())
                .role(request.getRole() != null ? request.getRole() : Role.USER).enabled(false)
                .verificationToken(UUID.randomUUID().toString()).build();

        switch (request.getRole()) {
        case CUSTOMER -> {
            Customer customer = new Customer();
            customer.setShippingAddress(request.getShippingAddress());
            customer.setBillingAddress(request.getBillingAddress());
            customer.setPreferredPaymentMethod(request.getPreferredPaymentMethod());
            customer.setUser(user);
            user.setCustomer(customer);
            customerRepository.save(customer);
        }
        case SUPPLIER -> {
            Supplier supplier = new Supplier();
            supplier.setCompanyName(request.getCompanyName());
            supplier.setBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
            supplier.setTaxId(request.getTaxId());
            supplier.setWarehouseAddress(request.getWarehouseAddress());
            supplier.setContactPerson(request.getContactPerson());
            supplier.setContactPhone(request.getContactPhone());
            supplier.setUser(user);
            user.setSupplier(supplier);
            supplierRepository.save(supplier);
        }
        case SHIPPER -> {
            Shipper shipper = new Shipper();
            shipper.setServiceArea(request.getServiceArea());
            shipper.setVehicleType(request.getVehicleType());
            shipper.setUser(user);
            user.setShipper(shipper);
            shipperRepository.save(shipper);
        }
        case ADMIN -> {
            Admin admin = new Admin();
            admin.setDepartment(request.getDepartment());
            admin.setAccessLevel(request.getAccessLevel());
            admin.setUser(user);
            user.setAdmin(admin);
            adminRepository.save(admin);
        }
        }

        // Send verification email
        try {
            emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());
        } catch (Exception e) {
            // Log the error but don't fail the registration
            System.err.println("Failed to send verification email: " + e.getMessage());
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            return AuthResponse.builder().success(false).message("Failed to register user").build();
        }

        return AuthResponse.builder().success(true)
                .message("Registration successful. Please check your email to verify your account.")
                .token(user.getVerificationToken())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.isEnabled()) {
                return AuthResponse.builder().success(false).message("Please verify your email before logging in")
                        .build();
            }

            var jwtToken = jwtService.generateToken(user);

            return AuthResponse.builder().success(true).token(jwtToken).message("Login successful").build();
        } catch (Exception e) {
            return AuthResponse.builder().success(false).message("Invalid email or password").build();
        }
    }

    public AuthResponse verifyEmail(String token) {
        try {

            Claims claims = jwtService.extractAllClaims(token);


            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("Token has expired");
            }

            return AuthResponse.builder()
                    .success(true)
                    .message("Token is valid")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }

          }
}
