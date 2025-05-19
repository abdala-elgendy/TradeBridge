package com.global.demo.controller;

import com.global.demo.dto.AuthResponse;
import com.global.demo.dto.LoginRequest;
import com.global.demo.dto.RegisterRequest;
import com.global.demo.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<AuthResponse> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok(authenticationService.verifyEmail(token));
    }
}