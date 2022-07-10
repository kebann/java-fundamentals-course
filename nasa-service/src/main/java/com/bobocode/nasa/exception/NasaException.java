package com.bobocode.nasa.exception;

public class NasaException extends RuntimeException {

    public NasaException(Throwable cause) {
        super(cause);
    }

    public NasaException(String message) {
        super(message);
    }
}
