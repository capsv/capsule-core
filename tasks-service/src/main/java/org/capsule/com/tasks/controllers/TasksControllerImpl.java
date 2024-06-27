package org.capsule.com.tasks.controllers;

import lombok.RequiredArgsConstructor;
import org.capsule.com.tasks.dtos.requests.TaskIdReqst;
import org.capsule.com.tasks.dtos.responses.ListOfTasksResp;
import org.capsule.com.tasks.services.TasksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/tasks")
@RequiredArgsConstructor
public class TasksControllerImpl implements ITasksController {

    private final TasksService tasksService;

    @Override
    public HttpStatus startTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return tasksService.startTask(token, request, bindingResult);
    }

    @Override
    public HttpStatus completeTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return tasksService.completeTask(token, request, bindingResult);
    }

    @Override
    public HttpStatus skipTask(String token, TaskIdReqst request, BindingResult bindingResult) {
        return tasksService.skipTask(token, request, bindingResult);
    }

    @Override
    public ResponseEntity<ListOfTasksResp> get(String token) {
        return tasksService.get(token);
    }
}
