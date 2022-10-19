package com.practice.todolist.security.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.todolist.repo.UserRepository;

@Service
public class ChangePasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 
     * @param email
     * @param oldPassword
     * @param newPassword
     */
    public void changePassword(String email, String oldPassword, String newPassword) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, oldPassword));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (userRepository.existsByEmail(email)) {
            if (bCryptPasswordEncoder.matches(oldPassword, userDetails.getPassword())) {
                SimpleDateFormat formatter = new SimpleDateFormat();
                UserDetailsImpl user = userRepository.findByEmail(email);
                user.setPassword(newPassword);
                user.setUpdatedTimeStamp(formatter.format(new Date()));
                userRepository.save(user);
            } else {
                throw new UsernameNotFoundException("Invalid password");
            }

        } else {
            throw new UsernameNotFoundException("Email not found!");
        }
    }

}
