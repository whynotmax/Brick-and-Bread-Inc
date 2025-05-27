package dev.mzcy.common.serialization.exception;

public class DeserializeException extends RuntimeException {
    public DeserializeException(String message, Throwable cause) {
      super(message, cause);
    }
}
