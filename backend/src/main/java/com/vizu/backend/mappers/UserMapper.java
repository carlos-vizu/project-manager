package com.vizu.backend.mappers;

import com.vizu.backend.application.controller.dto.response.UserSummaryResponse;
import com.vizu.backend.domain.model.User;

public class UserMapper {

    private UserMapper(){}

    public static UserSummaryResponse mapUserToUserResponse(User user) {
        UserSummaryResponse userResponse = new UserSummaryResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
