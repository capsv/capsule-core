package auth.service.dev.controllers.advices;

import static auth.service.dev.common.Constants.AUTHENTICATION_FAILURE_MESSAGE;
import static auth.service.dev.common.Constants.INTERNAL_SERVER_ERROR_MESSAGE;
import static auth.service.dev.common.Constants.NOT_FOUND_MESSAGE;
import static auth.service.dev.common.Constants.NOT_VALID_FIELDS_MESSAGE;
import static auth.service.dev.common.Constants.NOT_VALID_MESSAGE;

import auth.service.dev.common.Status;
import auth.service.dev.dtos.CommonDTO;
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
        return response(HttpStatus.BAD_REQUEST, Status.BAD_REQUEST, NOT_VALID_FIELDS_MESSAGE,
            e.getErrors());
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ResponseWrapper> handleException(NotFoundException e) {
        return response(HttpStatus.NOT_FOUND, Status.NOT_FOUND, NOT_FOUND_MESSAGE,
            List.of(new UserError(e.getMessage())));
    }

    @ExceptionHandler(TokenNotValidException.class)
    private ResponseEntity<ResponseWrapper> handleException(TokenNotValidException e) {
        return response(HttpStatus.BAD_REQUEST, Status.NOT_VALID, NOT_VALID_MESSAGE,
            Collections.emptyList());
    }

    @ExceptionHandler(NotAuthenticateException.class)
    private ResponseEntity<ResponseWrapper> handleException(NotAuthenticateException e) {
        return response(HttpStatus.BAD_REQUEST, Status.NOT_AUTHENTICATED,
            AUTHENTICATION_FAILURE_MESSAGE, List.of(new UserError(e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ResponseWrapper> handleException(RuntimeException e) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR,
            INTERNAL_SERVER_ERROR_MESSAGE, Collections.emptyList());
    }

    private ResponseEntity<ResponseWrapper> response(HttpStatus code, Status status, String message,
        List<? extends CommonDTO> errors) {
        return new ResponseEntity<>(
            ResponseWrapper.builder().status(status).message(message).time(LocalDateTime.now())
                .payload(errors).build(), code);
    }
}
