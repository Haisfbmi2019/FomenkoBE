package com.anton.fomenko.controllers;

import com.anton.fomenko.services.UserService;
import com.anton.fomenko.configs.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    @GetMapping
    public ResponseEntity<?> getUser(Authentication authentication) {
        Long userId = jwtTokenProvider.getUserId(authentication);
        return ResponseEntity.ok(userId);
    }

    @PutMapping("/change_password")
    public void processChangePasswordRequest(@RequestParam String oldPassword, @RequestParam String newPassword,
                                             Authentication authentication) {
        Long userId = jwtTokenProvider.getUserId(authentication);
        userService.changePassword(userId, oldPassword, newPassword);
    }

}
