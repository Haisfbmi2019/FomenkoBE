package com.anton.fomenko.configs.security.jwt;

import com.anton.fomenko.database.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return JwtUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .accountNonLocked(true)
                .password(user.getPassword())
                .authorities(user.getAuthority())
                .build();
    }

}
