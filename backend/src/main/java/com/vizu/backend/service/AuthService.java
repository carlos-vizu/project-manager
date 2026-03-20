package com.vizu.backend.service;

import org.springframework.http.ResponseEntity;

import com.vizu.backend.application.controller.dto.request.LoginRequest;

public interface AuthService {
	public ResponseEntity login(LoginRequest req);
}
