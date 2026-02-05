package com.example.goslint_judge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.goslint_judge.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
