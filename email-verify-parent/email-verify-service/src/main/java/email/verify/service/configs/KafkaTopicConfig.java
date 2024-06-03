package email.verify.service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic emailVerificationTopic() {
        return TopicBuilder.name(Constants.EMAIL_VERIFY_SENDER_TOPIC).partitions(1).replicas(1)
            .build();
    }

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name(Constants.EMAIL_VERIFY_AUTH_TOPIC).partitions(1).replicas(1)
            .build();
    }
}