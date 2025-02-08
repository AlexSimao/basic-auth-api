package com.estudo.springsecurity.dtos;

import org.springframework.beans.BeanUtils;

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

  public UserDTO(User usuario) {
    BeanUtils.copyProperties(usuario, this);
  }

  public User toEntity() {
    User entity = new User();
    BeanUtils.copyProperties(this, entity);
    return entity;
  }
}
