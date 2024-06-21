package org.capsule.com.controllers.advice;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import org.capsule.com.dtos.common.CommonDTO;
import org.capsule.com.dtos.errors.Error;
import org.capsule.com.dtos.errors.WrongMessage;
import org.capsule.com.dtos.responses.Wrapper;
import org.capsule.com.utils.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DetailsControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<Wrapper<WrongMessage>> handleException(NotFoundException ex) {
        return response(HttpStatus.NOT_FOUND, ex.getMessage(),
            List.of(new WrongMessage(ex.getField(), ex.getError())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Wrapper<Error>> handleConstraintViolationException(
        ConstraintViolationException ex) {
        return response(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of(new Error(ex.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<Wrapper<Error>> handleRuntimeException(RuntimeException ex) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), List.of(new Error(ex.getMessage())));
    }

    private <E extends CommonDTO> ResponseEntity<Wrapper<E>> response(HttpStatus status,
        String message, List<E> payload) {
        return new ResponseEntity<>(new Wrapper<>(status, message, LocalDateTime.now(), payload),
            status);
    }
}
