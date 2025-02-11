package com.estudo.springsecurity.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estudo.springsecurity.dtos.LoginRequestDTO;
import com.estudo.springsecurity.dtos.UserDTO;
import com.estudo.springsecurity.dtos.AuthResponseDTO;
import com.estudo.springsecurity.entities.Role;
import com.estudo.springsecurity.entities.User;
import com.estudo.springsecurity.infra.exceptions.EmailAlreadyInUseException;
import com.estudo.springsecurity.infra.exceptions.EntityNotFoundException;
import com.estudo.springsecurity.infra.security.TokenService;
import com.estudo.springsecurity.repositories.RoleRepository;
import com.estudo.springsecurity.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;
  private final RoleRepository roleRepository;

  private User toEntity(UserDTO userDTO) {
    User entity = new User();
    BeanUtils.copyProperties(userDTO, entity);

    // Converte as roles do DTO para entidades Role
    Set<Role> roles = new HashSet<>();
    if (userDTO.getRoles() != null) { // Verifica se roles foi enviado no request
      for (String roleName : userDTO.getRoles()) {
        Role role = roleRepository.findByName(roleName) // Busca a role no banco de dados
            .orElseThrow(() -> new EntityNotFoundException("Role não encontrada: " + roleName));
        roles.add(role);
      }
    }
    entity.setRoles(roles); // Define as roles para o usuário

    return entity;
  }

  public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
    User user = userRepository.findByEmail(loginRequestDTO.email())
        .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado."));

    if (!passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
      throw new IllegalArgumentException("Senha inválida.");
    }

    String token = tokenService.generateKeyToken(user);
    return new AuthResponseDTO(user.getName(), token, "Bem Vindo de Volta");
  }

  public AuthResponseDTO register(UserDTO userDTO) {
    Optional<User> user = userRepository.findByEmail(userDTO.getEmail());

    if (user.isPresent()) {
      throw new EmailAlreadyInUseException("Este Email já esta em uso.");
    }

    if (userDTO.getRoles() == null) { // Se não for enviado roles no request, define como USER
      Set<String> roles = new HashSet<>();
      roles.add("USER");
      userDTO.setRoles(roles);
    }

    User newUser = toEntity(userDTO);
    newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    userRepository.save(newUser);

    String token = tokenService.generateKeyToken(newUser);
    return new AuthResponseDTO(newUser.getName(), token, "Novo Usuario registrado com Sucesso");
  }

}
