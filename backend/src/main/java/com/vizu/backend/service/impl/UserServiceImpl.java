package com.vizu.backend.service.impl;

import com.vizu.backend.application.controller.dto.response.UserSummaryResponse;
import com.vizu.backend.domain.model.User;
import com.vizu.backend.infraestructure.repository.UserRepository;
import com.vizu.backend.mappers.UserMapper;
import com.vizu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserSummaryResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.mapUserToUserResponse(user);
    }

    public UserSummaryResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.mapUserToUserResponse(user);
    }

}