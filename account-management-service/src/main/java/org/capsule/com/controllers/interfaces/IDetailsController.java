package org.capsule.com.controllers.interfaces;

import java.util.Map;
import org.capsule.com.dtos.responses.Data;
import org.capsule.com.dtos.responses.Wrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IDetailsController {

    @GetMapping(headers = {"Authorization"}, produces = "application/json")
    ResponseEntity<Wrapper<Data>> get(@RequestHeader("Authorization") String token);

    @PatchMapping(headers = {"Authorization"},
        consumes = "application/json", produces = "application/json")
    ResponseEntity<Wrapper<Data>> partiallyUpdate(@RequestHeader("Authorization") String token,
        @RequestBody Map<String, Object> updates);

    @DeleteMapping(headers = {"Authorization"})
    ResponseEntity<HttpStatus> delete(@RequestHeader("Authorization") String token);
}
