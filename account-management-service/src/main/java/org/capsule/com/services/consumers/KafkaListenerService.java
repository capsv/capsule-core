package org.capsule.com.services.consumers;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.capsule.com.configs.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.capsule.com.services.DetailsService;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);
    private final DetailsService detailsService;

    @KafkaListener(topics = Constants.CREATE_NEW_ACCOUNT_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void consumer(ConsumerRecord<String, String> record, @Payload String username) {
        LOGGER.info("KAFKA [account-management-service] received message[{}] from [{}]", record.value(),
            record.topic());
        detailsService.createNewAccountFromKafka(username);
    }
}
