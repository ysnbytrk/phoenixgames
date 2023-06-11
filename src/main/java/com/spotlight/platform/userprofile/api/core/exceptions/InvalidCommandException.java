package com.spotlight.platform.userprofile.api.core.exceptions;

/**
 * Exception thrown when an invalid command is encountered.
 */
public class InvalidCommandException extends RuntimeException {

    /**
     * Constructs an InvalidCommandException with the specified error message.
     *
     * @param message The error message describing the invalid command.
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
