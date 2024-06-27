package org.capsule.com.tasks.controllers;

import jakarta.validation.Valid;
import org.capsule.com.tasks.dtos.requests.TaskIdReqst;
import org.capsule.com.tasks.dtos.responses.ListOfTasksResp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface ITasksController {

    @PostMapping(value = "/start", headers = {"Authorization"}, consumes = "application/json")
    HttpStatus startTask(@RequestHeader("Authorization") String token,
        @RequestBody @Valid TaskIdReqst request, BindingResult bindingResult);

    @PostMapping(value = "/complete", headers = {"Authorization"}, consumes = "application/json")
    HttpStatus completeTask(@RequestHeader("Authorization") String token,
        @RequestBody @Valid TaskIdReqst request, BindingResult bindingResult);

    @PostMapping(value = "/skip", headers = "Authorization", consumes = "application/json")
    HttpStatus skipTask(@RequestHeader("Authorization") String token,
        @RequestBody @Valid TaskIdReqst request, BindingResult bindingResult);

    @GetMapping(headers = {"Authorization"}, produces = "application/json")
    ResponseEntity<ListOfTasksResp> get(@RequestHeader("Authorization") String token);
}