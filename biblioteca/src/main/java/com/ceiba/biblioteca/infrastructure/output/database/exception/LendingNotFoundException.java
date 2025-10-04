package com.ceiba.biblioteca.infrastructure.output.database.exception;

public class LendingNotFoundException extends RuntimeException{
    public LendingNotFoundException(String message) {
        super(message);
    }
}
