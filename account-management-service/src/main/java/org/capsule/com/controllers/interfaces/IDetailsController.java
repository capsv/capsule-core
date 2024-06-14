package org.capsule.com.controllers.interfaces;

import java.util.Map;
import org.capsule.com.dtos.responses.Data;
import org.capsule.com.dtos.responses.Wrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IDetailsController {

    @GetMapping(value = "/{username}", produces = "application/json")
    ResponseEntity<Wrapper<Data>> get(@PathVariable("username") String username);

    @PatchMapping("/{username}")
    ResponseEntity<Wrapper<Data>> partiallyUpdate(@PathVariable("username") String username,
        @RequestBody Map<String, Object> updates);

    @DeleteMapping("/{username}")
    ResponseEntity<HttpStatus> delete(@PathVariable("username") String username);
}
