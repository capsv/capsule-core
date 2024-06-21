package org.capsule.com.services.producers;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.capsule.com.dtos.kafka.Letter;
import org.capsule.com.models.Verify;
import org.capsule.com.utils.exceptions.ProducerException;
import org.capsule.com.utils.mappers.VerifyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaJsonProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaJsonProducerService.class);
    private final KafkaTemplate<String, Object> kafkaJsonTemplate;
    private final VerifyMapper verifyMapper;

    public void produce(String topic, Verify verify) {
        Letter letter = verifyMapper.convertToDTO(verify);  //TODO починить маппер
        CompletableFuture<SendResult<String, Object>> future = kafkaJsonTemplate.send(topic, letter);
        future.thenAccept(result -> {
            LOGGER.info("KAFKA [email-verify-service] sent message [{}] to [{}]", letter, topic);
        }).exceptionally(ex -> {
            LOGGER.error("Unable to send message=[{}] due to : [{}]", letter, ex.getMessage());
            throw new ProducerException("some problem occurred while sending message");
        });
    }
}