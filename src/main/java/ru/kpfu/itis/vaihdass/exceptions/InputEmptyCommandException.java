package ru.kpfu.itis.vaihdass.exceptions;

public class InputEmptyCommandException extends Exception {
    public InputEmptyCommandException() {
    }

    public InputEmptyCommandException(String message) {
        super(message);
    }

    public InputEmptyCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputEmptyCommandException(Throwable cause) {
        super(cause);
    }

    public InputEmptyCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
