package org.capsule.com.controllers.advice;

import org.capsule.com.configs.Message;
import org.capsule.com.dtos.errors.SomeErrorMessage;
import org.capsule.com.services.producers.KafkaJsonProducerService;
import org.capsule.com.utils.exceptions.NotValidException;
import org.capsule.com.dtos.CommonDTO;
import org.capsule.com.dtos.RespWrapper;
import java.time.LocalDateTime;
import java.util.List;
import org.capsule.com.utils.exceptions.ProducerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VerifyControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaJsonProducerService.class);

    @ExceptionHandler(NotValidException.class)
    private ResponseEntity<RespWrapper> handleException(NotValidException e) {
        LOGGER.error(e.getMessage());
        return response(HttpStatus.BAD_REQUEST, Message.NOT_VALID_FIELDS_MSG, e.getErrors());
    }

    @ExceptionHandler(ProducerException.class)
    private ResponseEntity<RespWrapper> handleException(ProducerException e) {
        LOGGER.error(e.getMessage());
        var message = SomeErrorMessage.builder().message(e.getMessage()).build();
        return response(HttpStatus.INTERNAL_SERVER_ERROR, Message.PRODUCER_ERROR_MSG,
            List.of(message));
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<RespWrapper> handleException(RuntimeException e) {
        LOGGER.error(e.getMessage());
        var message = SomeErrorMessage.builder().message(e.getMessage()).build();
        return response(HttpStatus.INTERNAL_SERVER_ERROR, Message.INTERNAL_SERVER_ERROR_MSG,
            List.of(message));
    }

    private ResponseEntity<RespWrapper> response(HttpStatus httpStatus, String message,
        List<? extends CommonDTO> payload) {
        return new ResponseEntity<>(
            RespWrapper.builder().status(httpStatus).message(message).time(LocalDateTime.now())
                .payload(payload).build(), httpStatus);
    }
}