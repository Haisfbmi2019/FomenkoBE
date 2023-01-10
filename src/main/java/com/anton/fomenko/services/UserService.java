package com.anton.fomenko.services;

import com.anton.fomenko.database.entities.User;
import com.anton.fomenko.database.repositories.UserRepository;
import com.anton.fomenko.exceptions.InternalViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.anton.fomenko.exceptions.InternalViolationType.INVALID_PASSWORD_EXCEPTION;
import static com.anton.fomenko.exceptions.InternalViolationType.USER_NOT_FOUND_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new InternalViolationException(USER_NOT_FOUND_EXCEPTION)
        );
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email.toLowerCase()).orElseThrow(
                () -> new InternalViolationException(USER_NOT_FOUND_EXCEPTION)
        );
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        var user = getUserById(userId);

        validPassword(newPassword);
        validPassword(user, oldPassword);

        user.setPassword(passwordEncoder.encode(newPassword));
        save(user);
        log.info("Success change password for user: {}", user.getEmail());
    }

    public void setNewPassword(User user, String newPassword) {
        validPassword(newPassword);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        log.info("Success change password for user: {}", user.getEmail());
    }

    public void validPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InternalViolationException(INVALID_PASSWORD_EXCEPTION);
        }
    }

    public void validPassword(String password) {
        if (password.length() < 4) {
            throw new InternalViolationException(INVALID_PASSWORD_EXCEPTION);
        }
    }

}
