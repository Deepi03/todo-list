package com.practice.todolist.security.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.practice.todolist.dto.UserInfo;
import com.practice.todolist.repo.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> user = userRepository.findUserByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("UserInfo not found!"));
        return UserDetailsImpl.build(user);
    }
}
