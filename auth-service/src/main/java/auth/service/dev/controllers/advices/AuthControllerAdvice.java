package auth.service.dev.controllers.advices;

import auth.service.dev.common.Status;
import auth.service.dev.dtos.responses.token.RespWrapper;
import auth.service.dev.dtos.responses.errors.UserError;
import auth.service.dev.utils.exceptions.NotFoundException;
import auth.service.dev.utils.exceptions.NotValidException;
import auth.service.dev.utils.exceptions.TokenNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(NotValidException.class)
    private ResponseEntity<RespWrapper> handleException(NotValidException e){
        return ResponseEntity.badRequest()
                .body(
                  RespWrapper.builder()
                          .status(Status.ERROR)
                          .time(LocalDateTime.now())
                          .message("Not valid field(s)")
                          .payload(e.getErrors())
                          .build()
                );
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<RespWrapper> handleException(NotFoundException e){
        return new ResponseEntity<>(
                RespWrapper.builder()
                        .status(Status.ERROR)
                        .time(LocalDateTime.now())
                        .message("Not found user")
                        .payload(List.of(new UserError(e.getMessage())))
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(TokenNotValidException.class)
    private ResponseEntity<RespWrapper> handleException(TokenNotValidException e){
        return ResponseEntity.badRequest().body(
                RespWrapper.builder()
                        .status(Status.NOT_VALID)
                        .time(LocalDateTime.now())
                        .message("Token is not valid ")
                        .payload(Collections.emptyList())
                        .build()
        );
    }
}
