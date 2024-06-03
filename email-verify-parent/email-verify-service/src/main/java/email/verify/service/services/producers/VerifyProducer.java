package email.verify.service.services.producers;

import email.verify.service.models.Verify;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerifyProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyProducer.class);
    private final KafkaTemplate<String, Verify> kafkaTemplate;

    public void produce(Verify verify, String topic) {
        CompletableFuture<SendResult<String, Verify>> future = kafkaTemplate.send(topic, verify);
        future.thenAccept(result -> {
            LOGGER.info("Sent message=[{}] with offset=[{}]", verify,
                result.getRecordMetadata().offset());
        }).exceptionally(ex -> {
            LOGGER.error("Unable to send message=[{}] due to : {}", verify, ex.getMessage());
            throw new RuntimeException(ex);
        });
    }
}