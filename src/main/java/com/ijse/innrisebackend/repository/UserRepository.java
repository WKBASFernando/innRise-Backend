package com.ijse.innrisebackend.repository;

import com.ijse.innrisebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    String username(String username);
}
