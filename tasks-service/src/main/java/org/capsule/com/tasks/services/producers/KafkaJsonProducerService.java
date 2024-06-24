package org.capsule.com.tasks.services.producers;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaJsonProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaJsonProducerService.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void produce(String topic, Object object) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, object);
        future.thenAccept(result ->{
            LOGGER.info("KafkaJsonProducerService message [{}] sent to [{}]", object, topic);
        }).exceptionally(ex -> {
            LOGGER.error("KafkaJsonProducerService message [{}] failed", object, ex);
            return null;
        });
    }
}
