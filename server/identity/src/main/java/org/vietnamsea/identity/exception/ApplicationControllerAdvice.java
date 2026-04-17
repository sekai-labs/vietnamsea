package org.vietnamsea.identity.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vietnamsea.identity.common.dto.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ApplicationControllerAdvice {
  @ExceptionHandler(value = {
      AuthException.class,
      ActionFailedException.class,
      ValidationException.class
  })
  public ResponseEntity<ResponseObject<String>> applicationException(BaseException exception) {
    log.error(exception.getMessage());
    return ResponseEntity.status(HttpStatus.OK).body(exception.getErrors());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseObject<String>> handleValidationException(MethodArgumentNotValidException exception) {
    var messages = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());
    ResponseObject<String> responseError = new ResponseObject.Builder<String>()
        .success(false)
        .messages(messages)
        .code("VALIDATION_ERROR")
        .content(null)
        .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
  }

  @ExceptionHandler({
      UsernameNotFoundException.class,
      BadCredentialsException.class,
      LockedException.class,
      DisabledException.class,
      AccountStatusException.class,
      InsufficientAuthenticationException.class
  })
  public ResponseEntity<ResponseObject<String>> authenticationFailedException(AuthenticationException exception) {
    var responseError = new ResponseObject.Builder<String>()
        .success(false)
        .messages(exception.getMessage())
        .code("AUTH_FAILED")
        .content(null)
        .build();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseError);
  }

  @ExceptionHandler({
      AuthorizationDeniedException.class
  })
  public ResponseEntity<ResponseObject<String>> authenticationDeniedException(AccessDeniedException exception) {
    var responseError = new ResponseObject.Builder<String>()
        .success(false)
        .messages(exception.getMessage())
        .code("AUTH_FORBIDDEN")
        .content(null)
        .build();
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseError);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseObject<String>> somethingWrongException(Exception ex) {
    log.error("SOMETHING WRONG", ex);
    var responseError = new ResponseObject.Builder<String>()
        .success(false)
        .messages(ex.getMessage())
        .code("SOMETHING_WRONG")
        .content(null)
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
  }
}
