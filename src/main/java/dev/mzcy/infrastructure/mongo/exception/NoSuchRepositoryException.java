package dev.mzcy.infrastructure.mongo.exception;

/**
 * Custom exception class for handling repository-related errors in a MongoDB context.
 * This exception is thrown when a requested repository does not exist.
 * It extends the DatabaseException class, which is a custom exception
 * for database-related errors.
 */
public class NoSuchRepositoryException extends DatabaseException {

  /**
   * Constructs a new NoSuchRepositoryException with the specified detail message.
   *
   * @param message the detail message
   */
  public NoSuchRepositoryException(String message) {
    super(message);
  }

  /**
   * Constructs a new NoSuchRepositoryException with the specified detail message
   * and cause.
   *
   * @param message the detail message
   * @param cause   the cause of the exception
   */
  public NoSuchRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }
}
