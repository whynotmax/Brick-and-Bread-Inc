package dev.mzcy.infrastructure.mongo.exception;

/**
 * Custom exception class for handling database-related errors.
 * This class extends RuntimeException, allowing it to be thrown
 * without being declared in method signatures.
 */
public class DatabaseException extends RuntimeException {

    /**
     * Constructs a new DatabaseException with the specified detail message.
     *
     * @param message the detail message
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new DatabaseException with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
