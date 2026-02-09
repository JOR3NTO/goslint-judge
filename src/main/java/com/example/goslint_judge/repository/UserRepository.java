package com.example.goslint_judge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.goslint_judge.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
