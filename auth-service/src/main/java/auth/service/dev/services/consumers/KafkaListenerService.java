package auth.service.dev.services.consumers;

import auth.service.dev.configs.Topics;
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

    @KafkaListener(topics = Topics.SUBMIT_VERIFY_STATUS_TOPIC,
        containerFactory = "kafkaListenerStringContainerFactory")
    public void consumerSubmitStatusTopic(ConsumerRecord<String, String> record,
        @Payload String username) {
        log(record);
        peopleDBService.setIsConfirmStatusByUsername(username);
    }

    @KafkaListener(topics = Topics.SUBMIT_ASSAY_PASSED_STATUS_TOPIC,
        containerFactory = "kafkaListenerStringContainerFactory")
    public void consumerSubmitAssayPassedStatusTopic(ConsumerRecord<String, String> record,
        @Payload String username) {
        log(record);
        peopleDBService.setIsAssayStatusByUsername(username);
    }

    @KafkaListener(topics = Topics.DELETE_ACCOUNT_TOPIC,
        containerFactory = "kafkaListenerStringContainerFactory")
    public void consumerDeleteAccountTopic(ConsumerRecord<String, String> record,
        @Payload String username) {
        log(record);
        peopleDBService.deleteByUsername(username);
    }

    private void log(ConsumerRecord<String, String> record) {
        LOGGER.info("KAFKA [auth-service] received message[{}] from [{}]", record.value(),
            record.topic());
    }
}
