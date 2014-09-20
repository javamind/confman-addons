package com.ninjamind.confman.operation;

/**
 * @author Guillaume EHRET
 */
public class BuilderException extends RuntimeException {
    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }
}
