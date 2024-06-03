package email.verify.service.services.producers;

import email.verify.service.dtos.kafka.VerifyDTO;
import email.verify.service.models.Verify;
import email.verify.service.utils.mappers.VerifyMapper;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyProducer.class);
    private final KafkaTemplate<String, VerifyDTO> kafkaTemplate;
    private final VerifyMapper verifyMapper;


    public void produce(Verify verify, String topic) {
        VerifyDTO verifyDTO = verifyMapper.convertToDTO(verify);
        CompletableFuture<SendResult<String, VerifyDTO>> future = kafkaTemplate.send(topic, verifyDTO);
        future.thenAccept(result -> {
            LOGGER.info("Sent message=[{}] with offset=[{}]", verifyDTO,
                result.getRecordMetadata().offset());
        }).exceptionally(ex -> {
            LOGGER.error("Unable to send message=[{}] due to : {}", verifyDTO, ex.getMessage());
            return null;
        });
    }
}