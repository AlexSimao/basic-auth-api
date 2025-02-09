package com.estudo.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.springsecurity.dtos.LoginRequestDTO;
import com.estudo.springsecurity.dtos.AuthResponseDTO;
import com.estudo.springsecurity.dtos.UserDTO;

import com.estudo.springsecurity.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
    AuthResponseDTO result = authService.login(loginRequestDTO);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDTO> register(@RequestBody UserDTO userDTO) {
    AuthResponseDTO result = authService.register(userDTO);
    return ResponseEntity.ok(result);
  }

}
