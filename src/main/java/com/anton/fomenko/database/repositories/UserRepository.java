package com.anton.fomenko.database.repositories;

import com.anton.fomenko.database.entities.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> getByEmail(String email);

    @Query("select u from User u")
    Page<User> getAllUsers(Pageable pageable);

}
