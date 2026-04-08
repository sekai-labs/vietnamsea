package org.vietnamsea.contract.exception;

import org.vietnamsea.contract.common.dto.ResponseObject;

public class AuthException extends BaseException {

  public AuthException(String message) {
    super(message);
    errors = new ResponseObject.Builder<String>()
        .success(false)
        .messages(message)
        .code("AUTH_FAILED")
        .build();
  }

  public AuthException(String message, Throwable cause) {
    super(message, cause);
    errors = new ResponseObject.Builder<String>()
        .success(false)
        .messages(message)
        .code("AUTH_FAILED")
        .build();
  }

}
