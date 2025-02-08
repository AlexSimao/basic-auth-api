package com.estudo.springsecurity.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.springsecurity.dtos.LoginRequestDTO;
import com.estudo.springsecurity.dtos.ResponseDTO;
import com.estudo.springsecurity.dtos.UserDTO;
import com.estudo.springsecurity.entities.User;
import com.estudo.springsecurity.infra.security.TokenService;
import com.estudo.springsecurity.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
    User user = userRepository.findByEmail(loginRequestDTO.email())
        .orElseThrow(() -> new RuntimeException("User Not Found"));

    if (passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
      String token = tokenService.generateKeyToken(user);
      return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO userDTO) {
    Optional<User> user = userRepository.findByEmail(userDTO.getEmail());

    if (user.isEmpty()) {
      User newUser = userDTO.toEntity();
      newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
      userRepository.save(newUser);

      String token = tokenService.generateKeyToken(newUser);
      return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
    }

    return ResponseEntity.badRequest().build();
  }

}
