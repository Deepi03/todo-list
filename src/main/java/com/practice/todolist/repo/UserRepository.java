package com.practice.todolist.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.todolist.security.services.UserDetailsImpl;

@Repository
public interface UserRepository extends JpaRepository<UserDetailsImpl, Long> {
    Optional<UserDetailsImpl> findUserByEmail(String email);

    UserDetailsImpl findByEmail(String email);

    Boolean existsByEmail(String email);
}
