package profile.service.dev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import profile.service.dev.services.producers.KafkaProfileProducer;

@RestController
@RequestMapping("api/v1/users")
public class PeopleController {

    private final KafkaProfileProducer kafkaProfileProducer;

    public PeopleController(KafkaProfileProducer kafkaProfileProducer) {
        this.kafkaProfileProducer = kafkaProfileProducer;
    }

    @GetMapping("data")
    public ResponseEntity<String> test() {

        return new ResponseEntity<>("Hello, world", HttpStatus.OK);
    }
}