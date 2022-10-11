package com.practice.todolist.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.todolist.WebSecurityConfig;
import com.practice.todolist.dto.UserInfo;
import com.practice.todolist.payload.request.ChangePassword;
import com.practice.todolist.payload.request.SignupRequest;
import com.practice.todolist.payload.response.JwtResponse;
import com.practice.todolist.payload.response.MessageResponse;
import com.practice.todolist.repo.UserRepository;
import com.practice.todolist.security.jwt.JwtUtils;
import com.practice.todolist.security.services.ResetPasswordService;
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
    ResetPasswordService resetPasswordService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignupRequest signupRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signupRequest.getEmail(), signupRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), userDetails.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use! "));
        }
        UserInfo user = new UserInfo(signUpRequest.getEmail(),
                webSecurityConfig.passwordEncoder().encode(signUpRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("UserInfo registered successfully"));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword) {
        resetPasswordService.resetPassword(changePassword.getEmail(), changePassword.getOldPasssword(),
                webSecurityConfig.passwordEncoder().encode(changePassword.getNewPassword()));
        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }
}
