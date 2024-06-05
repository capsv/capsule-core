package org.capsule.com.services.consumers;

import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
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

    @KafkaListener(topics = "letters-with-code-topic", clientIdPrefix = "json",
        containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, Letter> record, @Payload Letter payload) {
        LOGGER.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}",
            record.key(), typeIdHeader(record.headers()), payload, record.toString());
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
            .filter(header -> header.key().equals("__TypeId__")).findFirst()
            .map(header -> new String(header.value())).orElse("N/A");
    }
}