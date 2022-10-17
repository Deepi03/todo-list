package com.practice.todolist.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.todolist.dto.Todo;

public interface TodoListRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findById(Long id);
}
