package com.estudo.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.estudo.springsecurity.dtos.UserDTO;
import com.estudo.springsecurity.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping()
  public ResponseEntity<Page<UserDTO>> getAll(Pageable pageable) {
    Page<UserDTO> result = userService.getAll(pageable);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getOne(@PathVariable String id) {
    UserDTO result = userService.getOne(id);
    return ResponseEntity.ok(result);
  }

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
    UserDTO result = userService.createUser(userDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

}
