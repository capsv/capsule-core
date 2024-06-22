package org.capsule.com.statistics.services.consumers;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.capsule.com.statistics.configs.Topics;
import org.capsule.com.statistics.services.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaStringListenerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaStringListenerService.class);
    private final StatisticsService statisticsService;

    @KafkaListener(topics = Topics.CREATE_NEW_ACCOUNT_TOPIC, containerFactory = "kafkaListenerStringContainerFactory")
    private void createNewAccountTopic(ConsumerRecord<String, String> record,
        @Payload String payload) {
        log(record, payload);
        statisticsService.createNewAccount(payload);
    }

    @KafkaListener(topics = Topics.DELETE_ACCOUNT_TOPIC, containerFactory = "kafkaListenerStringContainerFactory")
    private void deleteAccountTopic(ConsumerRecord<String, String> record,
        @Payload String payload) {
        log(record, payload);
        statisticsService.deleteAccount(payload);
    }

    private void log(ConsumerRecord<String, String> record, String payload) {
        LOGGER.info("KafkaStringListenerService received message [{}] from [{}]", payload,
            record.topic());
    }
}
