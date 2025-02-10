package com.estudo.springsecurity.infra.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estudo.springsecurity.entities.User;
import com.estudo.springsecurity.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    Set<GrantedAuthority> authorities = user.getRoles() == null ? Set.of()
        : user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())) // Converte Role para GrantedAuthority
            .collect(Collectors.toSet());

    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        authorities);
  }

}
