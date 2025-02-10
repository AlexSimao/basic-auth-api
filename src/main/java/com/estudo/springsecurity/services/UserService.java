package com.estudo.springsecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudo.springsecurity.dtos.UserDTO;
import com.estudo.springsecurity.entities.User;
import com.estudo.springsecurity.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Transactional(readOnly = true)
  public Page<UserDTO> getAll(Pageable pageable) {
    Page<User> result = userRepository.findAll(pageable);
    Page<UserDTO> dto = result.map(UserDTO::new);
    return dto;
  }

  @Transactional(readOnly = true)
  public UserDTO getOne(String id) {
    User result = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario com id: " + id + " não encontrado"));

    UserDTO dto = new UserDTO(result);
    return dto;
  }

}
