package com.ceiba.biblioteca.domain.spi;

import com.ceiba.biblioteca.domain.model.Lending;

public interface ILendingPersistencePort {
    boolean isUserWithBookLending(String userId);
    Lending saveLending (Lending lending);

    Lending getLendingById(int lendingId);
}
