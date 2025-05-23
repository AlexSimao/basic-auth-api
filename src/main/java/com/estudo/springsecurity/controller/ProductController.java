package com.estudo.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.springsecurity.dtos.ProductDTO;
import com.estudo.springsecurity.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<Page<ProductDTO>> getAll(Pageable pageable) {
    Page<ProductDTO> result = productService.getAll(pageable);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getOne(@PathVariable Long id) {
    ProductDTO result = productService.getOne(id);
    return ResponseEntity.ok(result);
  }

  @PostMapping
  public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
    ProductDTO result = productService.createProduct(productDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

}
