package com.ceiba.biblioteca.infrastructure.input.rest.middleware;

import com.ceiba.biblioteca.domain.exception.BadUserTypeException;
import com.ceiba.biblioteca.domain.exception.UserWithBookLendingException;
import com.ceiba.biblioteca.infrastructure.output.database.exception.LendingNotFoundException;
import com.ceiba.biblioteca.infrastructure.output.database.helper.InfrastructureConstants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice()
public class LendingControllerAdvisor {
    private static final String MESSAGE = "mensaje";

    @ExceptionHandler(UserWithBookLendingException.class)
    public ResponseEntity<Map<String, String>> userWithBookLending(UserWithBookLendingException userWithBookLendingException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap(MESSAGE, userWithBookLendingException.getMessage()));
    }

    @ExceptionHandler(BadUserTypeException.class)
    public ResponseEntity<Map<String, String>> badUserType(BadUserTypeException badUserTypeException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap(MESSAGE, badUserTypeException.getMessage()));
    }

    @ExceptionHandler(LendingNotFoundException.class)
    public ResponseEntity<Map<String, String>> lendingNotFound(LendingNotFoundException lendingNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap(MESSAGE, lendingNotFoundException.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(Collections.singletonMap(MESSAGE, ex.getBindingResult()
                        .getFieldErrors()
                        .get(0)
                        .getDefaultMessage()));
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<Map<String, String>> tpoUsuarioNoEntero() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap(MESSAGE, InfrastructureConstants.MSG_MISMATCHED_INPUT_EXCEPTION));
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Map<String, String>> badData(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap(MESSAGE, InfrastructureConstants.MSG_JSON_PARSE_EXCEPTION));
    }
}
