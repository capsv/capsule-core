package org.capsule.com.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic toEmailSenderTopic() {
        return TopicBuilder.name(Constants.TO_EMAIL_SENDER_TOPIC).partitions(1).replicas(1)
            .build();
    }

    @Bean
    public NewTopic toAuthTopic() {
        return TopicBuilder.name(Constants.TO_AUTH_TOPIC).partitions(1).replicas(1)
            .build();
    }
}