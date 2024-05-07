package auth.service.dev.services.consumers;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProfileConsumer {

    @KafkaListener(topics = "profile", groupId = "auth-profile-consumer")
    public void consumer(ConsumerRecord<String, String> record) {
        log.info(record.toString());
    }
}
