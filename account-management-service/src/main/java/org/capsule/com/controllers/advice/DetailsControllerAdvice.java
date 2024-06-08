package org.capsule.com.controllers.advice;

import java.time.LocalDateTime;
import java.util.List;
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
    public ResponseEntity<Wrapper<WrongMessage>> handleException(NotFoundException e) {
        return response(HttpStatus.NOT_FOUND, e.getMessage(),
            List.of(new WrongMessage(e.getField(), e.getError())));
    }

    private ResponseEntity<Wrapper<WrongMessage>> response(HttpStatus status, String message,
        List<WrongMessage> payload) {
        return new ResponseEntity<>(new Wrapper<>(status, message, LocalDateTime.now(), payload),
            status);
    }
}
