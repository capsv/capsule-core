package profile.service.dev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import profile.service.dev.models.Details;
import profile.service.dev.services.consumers.KafkaListenerService;

@RestController
@RequestMapping("api/v1/users")
public class PeopleController {

    @GetMapping(path = "/{username}")
    public ResponseEntity<Details> get(@PathVariable String username) {
        return null;
    }
}