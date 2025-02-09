package com.estudo.springsecurity.infra;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.estudo.springsecurity.dtos.ResponseExceptionErrorDTO;
import com.estudo.springsecurity.infra.exceptions.EmailAlreadyInUseException;
import com.estudo.springsecurity.infra.exceptions.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseExceptionErrorDTO> illegalArgument(IllegalArgumentException e,
      HttpServletRequest request) {

    ResponseExceptionErrorDTO err = new ResponseExceptionErrorDTO();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.BAD_REQUEST.value());
    err.setError("Bad Request");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseExceptionErrorDTO> entityNotFound(EntityNotFoundException e,
      HttpServletRequest request) {

    ResponseExceptionErrorDTO err = new ResponseExceptionErrorDTO();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.NOT_FOUND.value());
    err.setError("Not Found");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(EmailAlreadyInUseException.class)
  public ResponseEntity<ResponseExceptionErrorDTO> emailAlreadyInUse(EmailAlreadyInUseException e,
      HttpServletRequest request) {

    ResponseExceptionErrorDTO err = new ResponseExceptionErrorDTO();
    err.setTimestamp(Instant.now());
    err.setStatus(HttpStatus.OK.value());
    err.setError("Email Already In Use");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());

    return ResponseEntity.status(HttpStatus.OK).body(err);
  }

}