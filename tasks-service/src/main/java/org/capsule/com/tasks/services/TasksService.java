package org.capsule.com.tasks.services;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.capsule.com.tasks.dtos.errors.CustomError;
import org.capsule.com.tasks.dtos.requests.TaskIdReqst;
import org.capsule.com.tasks.dtos.responses.ListOfTasksResp;
import org.capsule.com.tasks.utils.exceptions.FieldsNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class TasksService implements ITasksService<TaskIdReqst, ListOfTasksResp> {

    private final DecodeJwtService decodeJwtService;

    public HttpStatus startTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        validate(bindingResult);
        String username = extractUsernameFromToken(token);
        return null;
    }

    public HttpStatus completeTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        validate(bindingResult);
        String username = extractUsernameFromToken(token);
        return null;
    }

    public HttpStatus skipTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        validate(bindingResult);
        String username = extractUsernameFromToken(token);
        return null;
    }

    public ResponseEntity<ListOfTasksResp> get(String token) {
        String username = extractUsernameFromToken(token);
        return null;
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<CustomError> errors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> new CustomError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

            throw new FieldsNotValidException(errors);
        }
    }

    private String extractUsernameFromToken(String token) {
        try {
            return decodeJwtService.extractUsername(token);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}