package org.capsule.com.controllers;

import org.capsule.com.models.Details;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class PeopleController {

    @GetMapping(path = "/{username}")
    public ResponseEntity<Details> get(@PathVariable String username) {
        return null;
    }
}