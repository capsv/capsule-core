package auth.service.dev.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic createNewAccountTopic() {
        return TopicBuilder.name(Topics.CREATE_NEW_ACCOUNT_TOPIC).partitions(1).replicas(1)
            .build();
    }
}
