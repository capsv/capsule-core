package org.capsule.com.tasks.services;

import lombok.RequiredArgsConstructor;
import org.capsule.com.tasks.dtos.requests.TaskIdReqst;
import org.capsule.com.tasks.dtos.responses.ListOfTasksResp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final DecodeJwtService decodeJwtService;

    public HttpStatus startTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return null;
    }

    public HttpStatus completeTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return null;
    }

    public HttpStatus skipTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return null;
    }

    public ResponseEntity<ListOfTasksResp> get(String token) {
        return null;
    }
}
