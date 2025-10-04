package com.ceiba.biblioteca.domain.helper;


public final class DomainConstants {
    private  DomainConstants() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static final String MSG_USER_WITH_BOOK_LENDING = "El usuario con identificación %s ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo";
    public static final String MSG_BAD_USER_TYPE = "Tipo de usuario no permitido en la biblioteca";
}
