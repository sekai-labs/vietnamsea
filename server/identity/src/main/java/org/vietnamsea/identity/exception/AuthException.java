package org.vietnamsea.identity.exception;

import org.vietnamsea.identity.common.dto.ResponseObject;

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
