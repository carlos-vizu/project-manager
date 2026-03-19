package com.vizu.backend.application.controller;

import com.vizu.backend.application.controller.dto.response.UserSummaryResponse;
import com.vizu.backend.application.controller.swagger.UserSwagger;
import com.vizu.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController implements UserSwagger {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSummaryResponse> getUser(@PathVariable Long id) {
        UserSummaryResponse user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
}
