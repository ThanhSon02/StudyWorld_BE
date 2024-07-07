package com.backend.studyworld.exceptions;

public class ExistException extends RuntimeException{
    public ExistException() {

    }

    public ExistException(String message) {
        super(message);
    }
}
