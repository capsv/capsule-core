package org.capsule.com.services.producers;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.capsule.com.utils.exceptions.ProducerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaStringProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaStringProducerService.class);
    private final KafkaTemplate<String, String> kafkaStringTemplate;

    public void produce(String topic, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaStringTemplate.send(topic,
            message);
        future.thenAccept(result -> {
            LOGGER.info("KAFKA [verify-email-service] sent message [{}] to [{}]",
                result.getProducerRecord().value(), result.getProducerRecord().topic());
        }).exceptionally(ex -> {
            LOGGER.error("Unable to send message: [{}] due to : [{}]", message, ex.getMessage());
            throw new ProducerException("some problem occurred while sending message");
        });
    }
}