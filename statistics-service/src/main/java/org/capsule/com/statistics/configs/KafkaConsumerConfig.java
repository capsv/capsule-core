package org.capsule.com.statistics.configs;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.capsule.com.statistics.dtos.Score;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> initConsumerProperties() {
        return kafkaProperties.buildConsumerProperties();
    }

    @Bean
    public Map<String, Object> consumerStringConfigs() {
        Map<String, Object> props = initConsumerProperties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return props;
    }

    @Bean
    public Map<String, Object> consumerJsonConfigs() {
        Map<String, Object> props = initConsumerProperties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS,
            "org.capsule.com.services.AssaysService$AssayResult:org.capsule.com.statistics.dtos.Score,org.capsule.com.tasks.dtos.responses.UpdateStatisticDto:org.capsule.com.statistics.dtos.UpdateStatisticDto");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return props;
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, String> consumerStringFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerStringConfigs());
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, Score> consumerJsonFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerJsonConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Score> kafkaListenerJsonContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Score> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerJsonFactory());

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerStringFactory());

        return factory;
    }
}
