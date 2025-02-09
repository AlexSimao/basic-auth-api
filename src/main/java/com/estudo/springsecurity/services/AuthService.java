package com.estudo.springsecurity.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estudo.springsecurity.dtos.LoginRequestDTO;
import com.estudo.springsecurity.dtos.UserDTO;
import com.estudo.springsecurity.dtos.AuthResponseDTO;
import com.estudo.springsecurity.entities.User;
import com.estudo.springsecurity.infra.security.TokenService;
import com.estudo.springsecurity.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
    User user = userRepository.findByEmail(loginRequestDTO.email())
        .orElseThrow(() -> new RuntimeException("User Not Found"));
    // Tratar exceção para alertar o usuario caso não esteja registrado.

    passwordEncoder.matches(loginRequestDTO.password(), user.getPassword());
    String token = tokenService.generateKeyToken(user);
    return new AuthResponseDTO(user.getName(), token, "Bem Vindo de Volta");
  }

  public AuthResponseDTO register(UserDTO userDTO) {
    Optional<User> user = userRepository.findByEmail(userDTO.getEmail());

    if (user.isEmpty()) {
      User newUser = userDTO.toEntity();
      newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
      userRepository.save(newUser);
      String token = tokenService.generateKeyToken(newUser);

      return new AuthResponseDTO(newUser.getName(), token, "Novo Usuario registrado com Sucesso");
    }

    // senha é inutil se eu só preciso do email para auterar a senha. tem que
    // criar exceção caso tentem criar usuario com email já registrado.
    user.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
    userRepository.save(user.get());
    String token = tokenService.generateKeyToken(user.get());
    return new AuthResponseDTO(user.get().getEmail(), token, "Usuario já tinha registro. Senha auterada.");
  }

}
