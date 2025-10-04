package com.ceiba.biblioteca.domain.usecase;

import com.ceiba.biblioteca.domain.exception.UserWithBookLendingException;
import com.ceiba.biblioteca.domain.helper.AuxiliaryMethods;
import com.ceiba.biblioteca.domain.model.Lending;
import com.ceiba.biblioteca.domain.model.UserType;
import com.ceiba.biblioteca.domain.spi.ILendingPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class LendingUseCaseTest {

    public static final int DAYS_LENDING_USER_AFFILIATE = 10;
    public static final int DAYS_LENDING_USER_EMPLOYEE = 8;
    public static final int DAYS_LENDING_USER_INVITED = 7;

    @Mock
    private ILendingPersistencePort lendingPersistencePort;

    @InjectMocks
    private LendingUseCase lendingUseCase;


    @Test
    void saveLendingSuccessfully() {
        Lending lending =new Lending("ISBN398547","123456", UserType.USER_AFFILIATE);
        LocalDate limitReturnBookDate = AuxiliaryMethods.sumWorkingDays(DAYS_LENDING_USER_AFFILIATE);
        when(lendingPersistencePort.isUserWithBookLending(lending.getUserId())).thenReturn(false);
        when(lendingPersistencePort.saveLending(lending)).thenReturn(lending);

        Lending result = lendingUseCase.saveLending(lending);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(limitReturnBookDate, result.getMaxReturnDate());
        verify(lendingPersistencePort).isUserWithBookLending(lending.getUserId());
        verify(lendingPersistencePort).saveLending(lending);
    }

    @Test
    void generateUserWithBookLendingExceptionIfUserHaveBookLending(){
        Lending lending =new Lending("ISBN398547","123456", UserType.USER_AFFILIATE);
        when(lendingPersistencePort.isUserWithBookLending(lending.getUserId())).thenReturn(true);
        assertThrows(UserWithBookLendingException.class, () -> {
            lendingUseCase.saveLending(lending);
        });
    }
}