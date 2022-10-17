package com.practice.todolist.security.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.practice.todolist.dto.Todo;
import com.practice.todolist.repo.TodoListRepository;
import com.practice.todolist.repo.UserRepository;

@Service
public class TodoListService {

    @Autowired
    TodoListRepository todoListRepository;

    @Autowired
    UserRepository userRepository;

    SimpleDateFormat formatter = new SimpleDateFormat();

    public List<Todo> getTodoList() {
        return todoListRepository.findAll();
    }

    public void createTodo(String name, String description, String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInName = authentication.getName();
        System.out.println();
        System.out.println("loggedInName :::::" + loggedInName);
        UserDetailsImpl ob = userRepository.findByEmail(loggedInName);

        Todo todo = new Todo(name, description, status);
        todo.setCreatedTimeStamp(formatter.format(new Date()));
        todo.setUser(ob);
        todoListRepository.save(todo);
    }

    public void updateTodo(Long id, String name, String description, String status) {
        Todo existingTodo = todoListRepository.getReferenceById(null);
        existingTodo.setName(name);
        existingTodo.setDescription(description);
        existingTodo.setUpdatedTimeStamp(formatter.format(new Date()));
        todoListRepository.save(existingTodo);
    }

    public void deleteTodo(String id) {
        todoListRepository.deleteById(Long.parseLong(id));
    }

}
