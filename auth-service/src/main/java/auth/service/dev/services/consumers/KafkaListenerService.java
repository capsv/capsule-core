package auth.service.dev.services.consumers;

import auth.service.dev.configs.Constants;
import auth.service.dev.services.PeopleDBService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);
    private final PeopleDBService peopleDBService;

    @KafkaListener(topics = Constants.SUBMIT_VERIFY_STATUS_TOPIC, clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void consumer(ConsumerRecord<String, String> record, @Payload String username) {
        LOGGER.info("KAFKA [auth-service] received message[{}] from [{}]", record.value(),
            record.topic());
        peopleDBService.setIsConfirmStatusByUsername(username);
    }
}
