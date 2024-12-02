package com.refeisoft.infra.exception;

public class MismatchedPasswordException extends RuntimeException {
    public MismatchedPasswordException(String message) {
        super(message);
    }
}
