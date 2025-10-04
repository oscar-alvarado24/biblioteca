package com.ceiba.biblioteca.application.handler;

import com.ceiba.biblioteca.application.dto.LendingBasicResponse;
import com.ceiba.biblioteca.application.dto.LendingRequest;

public interface ILendingHandler {
    LendingBasicResponse saveLending(LendingRequest lendingRequest);
}
