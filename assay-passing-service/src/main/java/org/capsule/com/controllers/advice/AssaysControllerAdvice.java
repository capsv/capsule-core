package org.capsule.com.controllers.advice;

import java.time.LocalDateTime;
import org.capsule.com.configs.Constants;
import org.capsule.com.dtos.Error;
import org.capsule.com.dtos.Wrapper;
import org.capsule.com.utils.exceptions.FieldsOfEntityIsNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AssaysControllerAdvice {

    @ExceptionHandler(FieldsOfEntityIsNotValidException.class)
    private ResponseEntity<Wrapper<Error>> handleException(FieldsOfEntityIsNotValidException ex) {
        var status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
            new Wrapper<>(status, Constants.FAILURE_MESSAGE, LocalDateTime.now(), ex.getErrors()),
            status);
    }
}
