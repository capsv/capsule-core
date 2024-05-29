package email.verify.service.controllers.advice;

import email.verify.service.configs.Message;
import email.verify.service.dtos.CommonDTO;
import email.verify.service.dtos.RespWrapper;
import email.verify.service.dtos.errors.SomeErrorMessage;
import email.verify.service.utils.exceptions.NotValidException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VerifyControllerAdvice {

    @ExceptionHandler(NotValidException.class)
    private ResponseEntity<RespWrapper> handleException(NotValidException e) {
        return response(HttpStatus.BAD_REQUEST, Message.NOT_VALID_FIELDS_MSG, e.getErrors());
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<RespWrapper> handleException(RuntimeException e) {
        var message = SomeErrorMessage.builder().message(e.getMessage()).build();
        return response(HttpStatus.INTERNAL_SERVER_ERROR, Message.INTERNAL_SERVER_ERROR_MSG,
            List.of(message));
    }

    private ResponseEntity<RespWrapper> response(HttpStatus httpStatus, String message,
        List<? extends CommonDTO> payload) {
        return new ResponseEntity<>(
            RespWrapper.builder().status(httpStatus).message(message).payload(payload).build(),
            httpStatus);
    }
}