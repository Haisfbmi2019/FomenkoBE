package com.anton.fomenko.services;

import com.anton.fomenko.exceptions.InternalViolationException;
import com.anton.fomenko.exceptions.InternalViolationType;
import com.anton.fomenko.models.AuthDetailsDto;
import com.anton.fomenko.models.LoginUserDto;
import com.anton.fomenko.configs.security.JwtUserDetailsService;
import com.anton.fomenko.configs.security.jwt.JwtTokenProvider;
import com.anton.fomenko.database.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserDetailsService jwtUserDetailsService;


    public AuthDetailsDto auth(LoginUserDto dto) {
        User user = userService.getUserByEmail(dto.getEmail().toLowerCase());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new InternalViolationException(InternalViolationType.INVALID_PASSWORD_EXCEPTION);
        }

        String token = jwtTokenProvider.createToken(user.getEmail());
        jwtUserDetailsService.saveUserToken(user, token);

        return new AuthDetailsDto(user.getId(), token);
    }

}
