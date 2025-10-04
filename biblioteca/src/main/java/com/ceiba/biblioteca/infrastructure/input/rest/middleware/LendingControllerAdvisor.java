package com.ceiba.biblioteca.infrastructure.input.rest.middleware;

import com.ceiba.biblioteca.domain.exception.BadUserTypeException;
import com.ceiba.biblioteca.domain.exception.UserWithBookLendingException;
import com.ceiba.biblioteca.infrastructure.output.database.exception.LendingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice()
public class LendingControllerAdvisor {
    private static final String MESSAGE = "mensaje";

    @ExceptionHandler(UserWithBookLendingException.class)
    public ResponseEntity<Map<String, String>> userWithBookLending(UserWithBookLendingException userWithBookLendingException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap(MESSAGE, userWithBookLendingException.getMessage()));
    }

    @ExceptionHandler(BadUserTypeException.class)
    public ResponseEntity<Map<String, String>> badUserType(BadUserTypeException badUserTypeException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap(MESSAGE, badUserTypeException.getMessage()));
    }

    @ExceptionHandler(LendingNotFoundException.class)
    public ResponseEntity<Map<String, String>> lendingNotFound(LendingNotFoundException lendingNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap(MESSAGE, lendingNotFoundException.getMessage()));
    }
}
