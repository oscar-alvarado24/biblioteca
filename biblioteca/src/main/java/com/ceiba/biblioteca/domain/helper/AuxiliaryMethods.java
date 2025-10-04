package com.ceiba.biblioteca.domain.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuxiliaryMethods {
    public static LocalDate sumWorkingDays(int daysAdd){
        LocalDate maxReturnDate = LocalDate.now();
        int daysAdded = 0;
        while (daysAdded < daysAdd){
            maxReturnDate = maxReturnDate.plusDays(1L);
            DayOfWeek dayOfWeek = maxReturnDate.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY){
                daysAdded++;
            }
        }
        return maxReturnDate;
    }
}
