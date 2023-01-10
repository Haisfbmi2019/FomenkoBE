package com.anton.fomenko.controllers;


import com.anton.fomenko.models.LoginUserDto;
import com.anton.fomenko.services.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto dto) {
        return ResponseEntity.ok(authService.auth(dto));
    }

}
