package com.estudo.springsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudo.springsecurity.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
