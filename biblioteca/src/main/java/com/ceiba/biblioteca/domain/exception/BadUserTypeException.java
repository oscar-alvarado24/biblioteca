package com.ceiba.biblioteca.domain.exception;

public class BadUserTypeException extends RuntimeException {
    public BadUserTypeException (String message){
        super(message);
    }
}
