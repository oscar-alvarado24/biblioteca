package com.ceiba.biblioteca.domain.exception;

public class UserWithBookLendingException extends RuntimeException {
    public UserWithBookLendingException(String message) {
        super(message);
    }
}
