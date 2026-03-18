package com.vizu.backend.service;

import com.vizu.backend.application.controller.dto.response.UserSummaryResponse;
import com.vizu.backend.domain.model.User;
import com.vizu.backend.mappers.UserMapper;

public interface UserService {

    public UserSummaryResponse findById(Long id);

    public UserSummaryResponse findByEmail(String email);

}
