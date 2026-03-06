package org.vietnamsea.identity.exception;

import org.vietnamsea.identity.common.dto.ResponseObject;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BaseException extends RuntimeException {
  protected ResponseObject<String> errors;

  public BaseException(String message) {
    super(message);
    log.error(message);
  }

  public BaseException(String message, Throwable cause) {
    super(message, cause);
    log.info(message);
    log.error(message);
  }
}
