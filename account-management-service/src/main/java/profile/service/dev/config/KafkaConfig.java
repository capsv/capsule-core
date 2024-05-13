package profile.service.dev.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic profileTopic() {
        return new NewTopic("profile", 1, (short) 1);
    }
}
