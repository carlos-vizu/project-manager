package com.vizu.backend.service.impl;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vizu.backend.application.controller.dto.request.LoginRequest;
import com.vizu.backend.domain.model.User;
import com.vizu.backend.infraestructure.repository.UserRepository;
import com.vizu.backend.infraestructure.security.JwtService;
import com.vizu.backend.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthServiceImpl(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

	public ResponseEntity login(LoginRequest req) {
		try {
			User user = userRepository.findByEmail(req.getEmail())
					.orElseThrow(() -> new RuntimeException("User not found"));

			if (!user.getPassword().equals(req.getPassword())) {
				throw new RuntimeException("Invalid password");
			}

			String token = jwtService.generateToken(user.getEmail());

			return ResponseEntity.ok(Map.of("token", token));

		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}

	}

    /*
     * 
     *     @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentials data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByEmail(username);

            var tokenResponse = new Token();
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, List.of("ROLE_USER"));
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
    */
     
}
