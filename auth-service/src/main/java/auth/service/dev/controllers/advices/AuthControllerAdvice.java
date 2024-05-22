package auth.service.dev.controllers.advices;

import auth.service.dev.common.Constants;
import auth.service.dev.common.Status;
import auth.service.dev.dtos.responses.errors.UserError;
import auth.service.dev.dtos.responses.tokens.ResponseWrapper;
import auth.service.dev.utils.exceptions.NotAuthenticateException;
import auth.service.dev.utils.exceptions.NotFoundException;
import auth.service.dev.utils.exceptions.NotValidException;
import auth.service.dev.utils.exceptions.TokenNotValidException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(NotValidException.class)
    private ResponseEntity<ResponseWrapper> handleException(NotValidException e) {
        return ResponseEntity.badRequest().body(
            ResponseWrapper.builder().status(Status.ERROR).time(LocalDateTime.now())
                .message("Not valid field(s)").payload(e.getErrors()).build());
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ResponseWrapper> handleException(NotFoundException e) {
        return new ResponseEntity<>(
            ResponseWrapper.builder().status(Status.ERROR).time(LocalDateTime.now())
                .message("Not found user").payload(List.of(new UserError(e.getMessage()))).build(),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenNotValidException.class)
    private ResponseEntity<ResponseWrapper> handleException(TokenNotValidException e) {
        return ResponseEntity.badRequest().body(
            ResponseWrapper.builder().status(Status.NOT_VALID).time(LocalDateTime.now())
                .message("Token is not valid ").payload(Collections.emptyList()).build());
    }

    @ExceptionHandler(NotAuthenticateException.class)
    private ResponseEntity<ResponseWrapper> handleException(NotAuthenticateException e) {
        return ResponseEntity.badRequest().body(
            ResponseWrapper.builder().status(Status.ERROR).time(LocalDateTime.now())
                .message(Constants.AUTHENTICATION_FAILURE)
                .payload(List.of(new UserError(e.getMessage()))).build());
    }
}
