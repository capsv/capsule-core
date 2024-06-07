package profile.service.dev.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic createNewAccountTopic() {
        return TopicBuilder.name("create-new-account-topic").partitions(0).replicas(0).build();
    }
}