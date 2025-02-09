package com.estudo.springsecurity.dtos;

import org.springframework.beans.BeanUtils;

import com.estudo.springsecurity.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
  private Long id;
  private String name;
  private Double price;

  public ProductDTO(Product entity) {
    BeanUtils.copyProperties(entity, this);
  }

  public Product toEntity() {
    Product entity = new Product();
    BeanUtils.copyProperties(this, entity);
    return entity;
  }

}
