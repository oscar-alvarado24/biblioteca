package com.ceiba.biblioteca.domain.usecase;

import com.ceiba.biblioteca.domain.api.ILendingServicePort;
import com.ceiba.biblioteca.domain.exception.UserWithBookLendingException;
import com.ceiba.biblioteca.domain.helper.AuxiliaryMethods;
import com.ceiba.biblioteca.domain.helper.DomainConstants;
import com.ceiba.biblioteca.domain.model.Lending;
import com.ceiba.biblioteca.domain.spi.ILendingPersistencePort;

import java.time.LocalDate;

public class LendingUseCase implements ILendingServicePort {

    private final ILendingPersistencePort lendingPersistencePort;

    public LendingUseCase(ILendingPersistencePort lendingPersistencePort) {
        this.lendingPersistencePort = lendingPersistencePort;
    }

    /**
     * Determinate if is possible lend a book and save the lending
     * @param lending object with data of request book lending
     * @return lending object with data of lending created
     */
    @Override
    public Lending saveLending(Lending lending) {
        if(lendingPersistencePort.isUserWithBookLending(lending.getUserId())){
            throw new UserWithBookLendingException(DomainConstants.MSG_USER_WITH_BOOK_LENDING);
        }
        int lendingDays = 0;
        switch (lending.getUserType()){
            case USER_AFFILIATE:
                lendingDays =10;
                break;
            case USER_EMPLOYEE:
                lendingDays = 8;
                break;
            case USER_INVITED:
                lendingDays = 7;
                break;
        }
        LocalDate maxReturnDate = AuxiliaryMethods.sumWorkingDays(lendingDays);
        lending.setMaxReturnDate(maxReturnDate);
        return lendingPersistencePort.saveLending(lending);
    }
}
