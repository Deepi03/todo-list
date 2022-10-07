package com.practice.todolist.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.todolist.dto.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findUserByEmail(String email);

    Boolean existsByEmail(String email);
}
