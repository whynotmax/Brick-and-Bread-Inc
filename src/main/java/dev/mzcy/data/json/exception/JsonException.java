package dev.mzcy.data.json.exception;

/**
 * Custom exception class for handling JSON-related errors.
 * This exception is thrown when there is an issue with JSON processing,
 * such as parsing errors or serialization issues.
 */
public class JsonException extends RuntimeException {

    /**
     * Constructs a new JsonException with the specified detail message.
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }
}