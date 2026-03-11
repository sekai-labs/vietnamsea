package org.vietnamsea.identity.exception;

import org.vietnamsea.identity.common.dto.ResponseObject;

public class ActionFailedException extends BaseException {
  public ActionFailedException(String message) {
    super(message);
    errors = new ResponseObject.Builder<String>()
        .code("ACTION_FAILED")
        .success(false)
        .messages(message)
        .build();
  }

  public ActionFailedException(String message, Throwable cause) {
    super(message, cause);
    errors = new ResponseObject.Builder<String>()
        .code("ACTION_FAILED")
        .success(false)
        .messages(message)
        .build();
  }
}
