package com.estudo.springsecurity.infra.exceptions;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String msg) {
    super(msg);
  }

}