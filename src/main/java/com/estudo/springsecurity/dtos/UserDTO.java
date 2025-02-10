package com.estudo.springsecurity.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.estudo.springsecurity.entities.Role;
import com.estudo.springsecurity.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
  private String id;
  private String name;
  private String email;
  private String password;
  private Set<String> roles;

  public UserDTO(User user) { // Construtor que recebe User
    if (user == null) {
      return; // Ou lance uma exceção, dependendo do seu caso
    }

    user.getRoles().size(); // Força o carregamento das roles

    BeanUtils.copyProperties(user, this);

    if (user.getRoles() != null) { // Verifica se roles não é nulo
      Set<String> roleNames = user.getRoles().stream()
          .map(Role::getName)
          .collect(Collectors.toSet());

      this.setRoles(roleNames);
    } else {
      this.setRoles(Set.of()); // Define um conjunto vazio se roles for nulo
    }
  }

}
