package com.ceiba.biblioteca.domain.api;

import com.ceiba.biblioteca.domain.model.Lending;

public interface ILendingServicePort {
    Lending saveLending (Lending lending);
}
