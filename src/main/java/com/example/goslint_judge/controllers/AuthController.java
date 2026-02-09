package com.example.goslint_judge.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goslint_judge.models.LoginRequest;
import com.example.goslint_judge.models.LoginResponse;
import com.example.goslint_judge.models.RegisterRequest;
import com.example.goslint_judge.models.UserResponse;
import com.example.goslint_judge.security.CustomUserDetails;
import com.example.goslint_judge.services.impl.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin // esto es para permitir solicitudes desde cualquier origen 
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    // perfil del usuario autenticado
    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse user = authService.getUserById(userDetails.getId());
        return ResponseEntity.ok(user);
    }
    /* 
    @PutMapping("/users/me")
    public ResponseEntity<UserResponse> updateCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateUserRequest request) {

        UserResponse updated = authService.updateUser(userDetails.getId(), request);
        return ResponseEntity.ok(updated);
    }    
    */

}
