package com.anton.fomenko.controllers;

import com.anton.fomenko.models.RegistrationUserDto;
import com.anton.fomenko.services.RegistrationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping
    public void registration(@Valid @RequestBody RegistrationUserDto data) {
        registrationService.register(data);
    }

}
