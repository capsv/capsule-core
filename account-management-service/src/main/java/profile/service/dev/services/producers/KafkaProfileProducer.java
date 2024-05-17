package profile.service.dev.services.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProfileProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProfileProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String token) {
        kafkaTemplate.send("profile", "tokens", token);
    }
}
