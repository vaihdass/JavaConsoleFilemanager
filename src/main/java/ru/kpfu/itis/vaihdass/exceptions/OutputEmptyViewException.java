package ru.kpfu.itis.vaihdass.exceptions;

public class OutputEmptyViewException extends RuntimeException {
    public OutputEmptyViewException() {
    }

    public OutputEmptyViewException(String message) {
        super(message);
    }

    public OutputEmptyViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutputEmptyViewException(Throwable cause) {
        super(cause);
    }

    public OutputEmptyViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
