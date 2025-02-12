package com.estudo.springsecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.estudo.springsecurity.dtos.ProductDTO;
import com.estudo.springsecurity.entities.Product;
import com.estudo.springsecurity.infra.exceptions.EntityNotFoundException;
import com.estudo.springsecurity.repositories.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Secured({ "ROLE_ADMIN", "ROLE_USER" })
  public Page<ProductDTO> getAll(Pageable pageable) {
    Page<Product> result = productRepository.findAll(pageable);
    Page<ProductDTO> dto = result.map(ProductDTO::new);
    return dto;
  }

  @Secured({ "ROLE_ADMIN", "ROLE_USER" })
  public ProductDTO getOne(Long id) {
    Product result = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Produto com id: " + id + " não encontrado."));

    ProductDTO dto = new ProductDTO(result);
    return dto;
  }

  @Secured({ "ROLE_ADMIN", "ROLE_USER" })
  public ProductDTO createProduct(ProductDTO productDTO) {
    if (productDTO.getName().isBlank() || productDTO.getName() == null) {
      throw new IllegalArgumentException("Nome do produto não pode ser vazio ou 'null': " + productDTO.getName());
    }
    if (productDTO.getPrice() == null) {
      throw new IllegalArgumentException("Preço do produto invalido: " + productDTO.getPrice());
    }

    Product result = productRepository.save(productDTO.toEntity());

    ProductDTO dto = new ProductDTO(result);
    return dto;
  }

}
