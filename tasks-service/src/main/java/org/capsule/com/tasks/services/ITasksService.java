package org.capsule.com.tasks.services;

import org.capsule.com.tasks.dtos.CoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface ITasksService<E extends CoDto, I extends CoDto> {

    HttpStatus startTask(String token, E request, BindingResult bindingResult);

    HttpStatus completeTask(String token, E request, BindingResult bindingResult);

    HttpStatus skipTask(String token, E request, BindingResult bindingResult);

    ResponseEntity<I> get(String token);
}