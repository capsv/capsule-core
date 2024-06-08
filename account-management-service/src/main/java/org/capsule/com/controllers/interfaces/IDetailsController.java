package org.capsule.com.controllers.interfaces;


import org.capsule.com.models.Details;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface IDetailsController {

    @GetMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Details> get(@PathVariable("username") String username);
}
