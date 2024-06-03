package email.verify.sender.service.services.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import email.verify.sender.service.config.Constants;
import email.verify.sender.service.models.VerifyDTO;
import email.verify.sender.service.services.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyConsumer.class);
    private final MailSenderService mailSenderService;

    @KafkaListener(topics = "email-verify-sender-topic", groupId = "email-verify-group")
    public void listen(String message) {
        LOGGER.info("Received raw message: {}", message);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            VerifyDTO verifyDTO = objectMapper.readValue(message, VerifyDTO.class);
            LOGGER.info("Deserialized message: {}", verifyDTO);
            mailSenderService.send(verifyDTO.getEmail(), Constants.EMAIL_VERIFICATION_SUBJECT, verifyDTO.getEmail());
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to deserialize message", e);
        }
    }
}
