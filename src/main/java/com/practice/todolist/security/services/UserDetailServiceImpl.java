package com.practice.todolist.security.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.todolist.repo.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDetailsImpl> user = userRepository.findUserByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("UserInfo not found!"));
        UserDetailsImpl userDetail = user.get();
        return new UserDetailsImpl(userDetail.getEmail(), userDetail.getPassword());
    }

}
