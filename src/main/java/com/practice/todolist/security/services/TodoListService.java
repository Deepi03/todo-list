package com.practice.todolist.security.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.practice.todolist.dto.EStatus;
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

    /**
     * 
     * @return All Todo List
     */
    public List<Todo> getTodoList() {
        return todoListRepository.findAll();
    }

    /**
     * 
     * @param name
     * @param description
     * @param status
     */

    public void createTodo(String name, String description, String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInName = authentication.getName();
        UserDetailsImpl ob = userRepository.findByEmail(loggedInName);

        Todo todo = new Todo(name, description, status);
        todo.setCreatedTimeStamp(formatter.format(new Date()));
        todo.setUser(ob);
        todoListRepository.save(todo);
    }

    /**
     * 
     * @param id
     * @param name
     * @param description
     * @param status
     */

    public void updateTodo(Long id, String name, String description, String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInName = authentication.getName();
        UserDetailsImpl ob = userRepository.findByEmail(loggedInName);
        Todo existingTodo = todoListRepository.getReferenceById(id);
        existingTodo.setName(name);
        existingTodo.setDescription(description);
        existingTodo.setStatus(EStatus.valueOf(status));
        existingTodo.setUpdatedTimeStamp(formatter.format(new Date()));
        existingTodo.setUser(ob);

        todoListRepository.save(existingTodo);
    }

    /**
     * 
     * @param id
     */

    public void deleteTodo(Long id) {
        todoListRepository.deleteById(id);
    }

}
