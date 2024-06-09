package org.capsule.com.services.producers;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void produce(String topic, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.thenAccept(result -> {
            LOGGER.info("KAFKA [account-management-service] sent message [{}] to [{}]",
                result.getProducerRecord().value(), result.getRecordMetadata().topic());
        }).exceptionally(ex ->{
            LOGGER.error("Something went wrong", ex);
            return null;
        });
    }
}
