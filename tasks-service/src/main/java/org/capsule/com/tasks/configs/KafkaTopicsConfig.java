package org.capsule.com.tasks.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic tasksTopic() {
        return TopicBuilder.name(Topics.TASKS_DATA_TOPIC)
            .partitions(1)
            .replicas(1)
            .build();
    }
}
