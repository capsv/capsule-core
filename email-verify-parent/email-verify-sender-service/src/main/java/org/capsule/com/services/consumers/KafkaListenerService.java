package org.capsule.com.services.consumers;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.capsule.com.config.Constants;
import org.capsule.com.models.Letter;
import org.capsule.com.services.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);
    private final MailSenderService mailSenderService;

    @KafkaListener(topics = "letters-with-code-topic", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, Letter> record, @Payload Letter payload) {
        LOGGER.info("Logger [JSON] received key {}: Payload: {} | Record: {}", record.key(),
            payload, record.toString());
        mailSenderService.send(payload.email(), Constants.EMAIL_VERIFICATION_SUBJECT,
            payload.username(), Integer.toString(payload.code()));
    }
}