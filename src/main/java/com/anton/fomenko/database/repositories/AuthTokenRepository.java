package com.anton.fomenko.database.repositories;

import com.anton.fomenko.database.entities.AuthToken;
import com.anton.fomenko.database.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthTokenRepository extends CrudRepository<AuthToken, Long> {
    Optional<AuthToken> getByUser(User user);
    Optional<AuthToken> getByUserId(Long user_id);
}
