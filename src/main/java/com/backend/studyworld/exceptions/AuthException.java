package com.backend.studyworld.exceptions;

public class AuthException extends RuntimeException{
    public AuthException() {

    }

    public AuthException(String message) {
        super(message);
    }
}
