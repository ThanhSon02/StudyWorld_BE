package com.backend.studyworld.exceptions;

public class InternalException extends RuntimeException{
    public InternalException(String message) {
        super(message);
    }

    public InternalException() {
    }
}
