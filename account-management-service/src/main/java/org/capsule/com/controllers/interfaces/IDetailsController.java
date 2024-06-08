package org.capsule.com.controllers.interfaces;

import org.capsule.com.dtos.responses.Data;
import org.capsule.com.dtos.responses.Wrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface IDetailsController {

    @GetMapping(value = "/{username}", produces = "application/json")
    ResponseEntity<Wrapper<Data>> get(@PathVariable("username") String username);
}
