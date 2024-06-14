package org.capsule.com.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic lettersWithCodeTopic() {
        return TopicBuilder.name(Constants.LETTERS_WITH_CODE_TOPIC).partitions(1).replicas(1)
            .build();
    }

    @Bean
    public NewTopic submitVerifyStatusTopic() {
        return TopicBuilder.name(Constants.SUBMIT_VERIFY_STATUS_TOPIC).partitions(1).replicas(1)
            .build();
    }
}