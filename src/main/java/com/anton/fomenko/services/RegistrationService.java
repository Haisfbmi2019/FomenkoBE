package com.anton.fomenko.services;

import com.anton.fomenko.database.entities.User;
import com.anton.fomenko.exceptions.InternalViolationException;
import com.anton.fomenko.exceptions.InternalViolationType;
import com.anton.fomenko.models.RegistrationUserDto;
import com.anton.fomenko.database.enums.UserRole;
import com.anton.fomenko.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void register(RegistrationUserDto registrationUserDto) {

        validSignUpRequest(registrationUserDto);

        User user = createUser(registrationUserDto);
        userRepository.save(user);

        log.info("User with email: {}, was success registered", user.getEmail());
    }

    private User createUser(RegistrationUserDto dto) {
        var user = new User();

        user.setEmail(dto.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.USER);

        return user;
    }

    private void validSignUpRequest(RegistrationUserDto registrationUserDto) {
        userRepository.getByEmail(registrationUserDto.getEmail().toLowerCase()).ifPresent(user -> {
            throw new InternalViolationException(InternalViolationType.EMAIL_IN_USE_EXCEPTION);
        });
    }

}
