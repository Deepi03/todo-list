package com.practice.todolist.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.todolist.WebSecurityConfig;
import com.practice.todolist.payload.request.ChangePassword;
import com.practice.todolist.payload.request.LoginRequest;
import com.practice.todolist.payload.request.SignupRequest;
import com.practice.todolist.payload.request.TodoRequest;
import com.practice.todolist.payload.response.JwtResponse;
import com.practice.todolist.payload.response.MessageResponse;
import com.practice.todolist.repo.UserRepository;
import com.practice.todolist.security.jwt.JwtUtils;
import com.practice.todolist.security.services.ChangePasswordService;
import com.practice.todolist.security.services.TodoListService;
import com.practice.todolist.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ChangePasswordService changePasswordService;

    @Autowired
    TodoListService todoListService;

    /**
     * 
     * @param loginRequest
     * @return response with java web token
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), userDetails.getPassword()));
    }

    /**
     * 
     * @param signUpRequest
     * @return Success Message
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use! "));
        }
        UserDetailsImpl user = new UserDetailsImpl(signUpRequest.getEmail(),
                webSecurityConfig.passwordEncoder().encode(signUpRequest.getPassword()));
        userRepository.save(user);
        System.out.println("SignUp" + user);
        return ResponseEntity.ok(new MessageResponse("UserInfo registered successfully"));
    }

    /**
     * 
     * @param changePassword
     * @return Success Message
     */
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword) {
        changePasswordService.changePassword(changePassword.getEmail(), changePassword.getOldPasssword(),
                webSecurityConfig.passwordEncoder().encode(changePassword.getNewPassword()));

        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }

    /**
     * 
     * @return All Todo List
     */

    @GetMapping("/todos")
    public ResponseEntity<?> displayTodoList() {
        return ResponseEntity.ok(todoListService.getTodoList());
    }

    /**
     * 
     * @param todo
     * @return Success Message
     */
    @PostMapping("/todos")
    public ResponseEntity<?> addTodo(@Valid @RequestBody TodoRequest todo) {
        todoListService.createTodo(todo.getName(), todo.getDescription(), todo.getStatus());
        return ResponseEntity.ok(new MessageResponse("Todo item Created successfully"));
    }

    /**
     * 
     * @param updateTodoRequest
     * @param id
     * @return Success Message
     */

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@Valid @RequestBody TodoRequest updateTodoRequest,
            @PathVariable Long id) {
        todoListService.updateTodo(id, updateTodoRequest.getName(),
                updateTodoRequest.getDescription(), updateTodoRequest.getStatus());
        return ResponseEntity.ok(new MessageResponse("Todo item updated successfully!"));
    }

    /**
     * 
     * @param id
     * @return Success Message
     */

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@Valid @PathVariable Long id) {
        todoListService.deleteTodo(id);
        return ResponseEntity.ok(new MessageResponse("Todo item deleted successfully!"));
    }
}
