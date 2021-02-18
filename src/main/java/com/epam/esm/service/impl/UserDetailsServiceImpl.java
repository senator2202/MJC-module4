package com.epam.esm.service.impl;

import com.epam.esm.controller.security.SecurityUser;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public SecurityUser loadUserByUsername(String userName) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        return optionalUser
                .map(u -> new SecurityUser(
                        u.getUserName(),
                        u.getPassword(),
                        roleToGrantedAuthorities(u.getRole()),
                        u.getId(), u.getRole().getName().equals("ADMIN")))
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User '%s' not found", userName)));
    }

    private Set<? extends GrantedAuthority> roleToGrantedAuthorities(Role role) {
        return role.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getName()))
                .collect(Collectors.toSet());
    }
}
