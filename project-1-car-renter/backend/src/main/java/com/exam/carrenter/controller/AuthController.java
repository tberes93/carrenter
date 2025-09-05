package com.exam.carrenter.controller;

import com.exam.carrenter.model.User;
import com.exam.carrenter.model.dto.RegisterRequest;
import com.exam.carrenter.services.AuthService;
import com.exam.carrenter.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.exam.carrenter.model.dto.AuthResponse;
import com.exam.carrenter.model.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;


    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService; this.jwtService = jwtService; }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            User u = authService.register(req.email(), req.password());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        User u = authService.authenticate(req.email(), req.password());
        return ResponseEntity.ok(new AuthResponse(jwtService.generate(u)));
    }
}
