package org.capsule.com.statistics.services.consumers;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.capsule.com.statistics.configs.Topics;
import org.capsule.com.statistics.dtos.Score;
import org.capsule.com.statistics.services.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaJsonListenerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaJsonListenerService.class);
    private final StatisticsService statisticsService;

    @KafkaListener(topics = Topics.SCORE_AFTER_ASSAY_TOPIC, containerFactory = "kafkaListenerJsonContainerFactory")
    public void receiveScoreAfterAssay(ConsumerRecord<String, Score> record,
        @Payload Score payload) {
        LOGGER.info("KafkaJsonListenerService received message [{}] from [{}]", payload,
            record.topic());
        statisticsService.setScore(payload);
    }
}
