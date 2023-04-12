package ru.kpfu.itis.vaihdass.exceptions;

public class IncorrectDirectoryException extends RuntimeException {
    public IncorrectDirectoryException() {
    }

    public IncorrectDirectoryException(String message) {
        super(message);
    }

    public IncorrectDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectDirectoryException(Throwable cause) {
        super(cause);
    }

    public IncorrectDirectoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
