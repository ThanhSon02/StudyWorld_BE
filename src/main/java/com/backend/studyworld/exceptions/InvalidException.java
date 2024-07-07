package com.backend.studyworld.exceptions;

public class InvalidException extends RuntimeException{
    public InvalidException(String message) {
        super(message);
    }

    public InvalidException() {
    }
}
