package com.practice.todolist.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.todolist.dto.UserInfo;
import com.practice.todolist.repo.UserRepository;

@Service
public class ResetPasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void resetPassword(String email, String oldPassword, String newPassword) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, oldPassword));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (userRepository.existsByEmail(email)) {
            if (bCryptPasswordEncoder.matches(oldPassword, userDetails.getPassword())) {
                System.out
                        .println(" ++++ reset password service" + " " + " " + " old password" + " "
                                + userDetails.getPassword()
                                + " " + "incoming old password" + " " + oldPassword);
                UserInfo user = userRepository.findByEmail(email);

                System.out.println("user" + user);
                user.setPassword(newPassword);
                userRepository.save(user);
            } else {
                throw new UsernameNotFoundException("Invalid password");
            }

        } else {
            throw new UsernameNotFoundException("Email not found!");
        }
    }

}