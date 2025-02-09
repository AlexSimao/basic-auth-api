package com.estudo.springsecurity.infra.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
  public EmailAlreadyInUseException(String msg) {
    super(msg);
  }

}
