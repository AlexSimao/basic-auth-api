package com.estudo.springsecurity.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudo.springsecurity.entities.User;


public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);

}
