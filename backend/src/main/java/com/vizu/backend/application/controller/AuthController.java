package com.vizu.backend.application.controller;

import com.vizu.backend.application.controller.dto.request.LoginRequest;
import com.vizu.backend.domain.model.User;
import com.vizu.backend.infraestructure.repository.UserRepository;
import com.vizu.backend.infraestructure.security.JwtService;
import com.vizu.backend.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (checkIfParamsIsNotNull(req)) {
        	return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid request!");
        }
        var token = service.login(req);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid request!");
        return token;
    }
    

    private boolean checkIfParamsIsNotNull(LoginRequest data) {
        return data == null || data.getEmail() == null || data.getEmail().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
    
}