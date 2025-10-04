package com.ceiba.biblioteca.domain.model;

import java.time.LocalDate;

public class Lending {
    private int id;
    private LocalDate maxReturnDate;
    private final String isbn;
    private final String userId;
    private final UserType userType;

    public Lending(String isbn, String userId, UserType userType) {
        this.isbn = isbn;
        this.userId = userId;
        this.userType = userType;
    }

    public Lending(int id, LocalDate maxReturnDate, String isbn, String userId, UserType userType) {
        this.id = id;
        this.maxReturnDate = maxReturnDate;
        this.isbn = isbn;
        this.userId = userId;
        this.userType = userType;
    }

    public int getId(){
        return this.id;
    }
    public LocalDate getMaxReturnDate() {
        return maxReturnDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getUserId(){
        return this.userId;
    }
    public UserType getUserType() {
        return this.userType;
    }

    public void setMaxReturnDate(LocalDate date){
        this.maxReturnDate = date;
    }
}
