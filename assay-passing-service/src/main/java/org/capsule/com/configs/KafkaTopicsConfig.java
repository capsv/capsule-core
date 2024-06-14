package org.capsule.com.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic assayPassedTopic() {
        return TopicBuilder.name(Constants.SUBMIT_ASSAY_PASSED_STATUS_TOPIC)
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic levelAfterAssayTopic() {
        return TopicBuilder.name(Constants.LEVEL_AFTER_ASSAY_TOPIC)
            .partitions(1)
            .replicas(1)
            .build();
    }
}
