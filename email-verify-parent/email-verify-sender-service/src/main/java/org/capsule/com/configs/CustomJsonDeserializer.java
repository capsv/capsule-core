package org.capsule.com.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomJsonDeserializer<T> implements Deserializer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomJsonDeserializer.class);

    private final ObjectMapper objectMapper;
    private final Class<T> targetType;

    public CustomJsonDeserializer(Class<T> targetType) {
        this.targetType = targetType;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, targetType);
        } catch (Exception e) {
            LOGGER.error("Error deserializing JSON message", e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}