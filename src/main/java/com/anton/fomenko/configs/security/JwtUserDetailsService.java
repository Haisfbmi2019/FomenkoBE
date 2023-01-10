package com.anton.fomenko.configs.security;

import com.anton.fomenko.database.entities.AuthToken;
import com.anton.fomenko.database.entities.User;
import com.anton.fomenko.database.repositories.AuthTokenRepository;
import com.anton.fomenko.database.repositories.UserRepository;
import com.anton.fomenko.exceptions.InternalViolationException;
import com.anton.fomenko.exceptions.InternalViolationType;
import com.anton.fomenko.configs.security.jwt.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthTokenRepository authTokenRepository;


    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.getByEmail(email.toLowerCase()).orElse(null);
        return JwtUserFactory.create(user);
    }

    public void saveUserToken(User user, String token) {
        AuthToken authToken = authTokenRepository.getByUser(user)
                .orElseGet(() -> {
                    var newToken = new AuthToken();
                    newToken.setUser(user);
                    return newToken;
                });
        authToken.setToken(token);
        authTokenRepository.save(authToken);
    }

    public String getUserToken(Long userId) {
        AuthToken authToken = authTokenRepository.getByUserId(userId).orElseThrow(
                () -> new InternalViolationException(InternalViolationType.USER_NOT_FOUND_EXCEPTION)
        );
        return authToken.getToken();
    }

    public AuthToken getUserAuthTokenOrCreateNew(User user, String token) {
        return authTokenRepository.getByUser(user).orElseGet(
                () -> {
                    var authToken = new AuthToken();
                    authToken.setToken(token);
                    authToken.setUser(user);
                    return authTokenRepository.save(authToken);
                }
        );
    }

}
