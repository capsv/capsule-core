package org.capsule.com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.capsule.com.common.Message;
import org.capsule.com.configs.Constants;
import org.capsule.com.dtos.common.CommonDTO;
import org.capsule.com.dtos.responses.Data;
import org.capsule.com.dtos.responses.Wrapper;
import org.capsule.com.models.Details;
import org.capsule.com.services.producers.KafkaProducerService;
import org.capsule.com.utils.mappers.DetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(DetailsService.class);
    private final GenerateFirstSecondNameService generateFirstSecondNameService;
    private final DetailsDBService detailsDBService;
    private final KafkaProducerService kafkaProducerService;
    private final DetailsMapper detailsMapper;
    private final ObjectMapper objectMapper;

    public void createNewAccountFromKafka(String username) {
        List<String> words = Arrays.asList(null, null);
        try {
            words = generateFirstSecondNameService.get();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            LOGGER.error("Error generating first and second names", ex);
        }

        Details details = Details.builder()
            .username(username)
            .firstName(words.get(0))
            .secondName(words.get(1))
            .age(null)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        LOGGER.info("DetailsService [account-management-service] create entity [{}]", details);
        detailsDBService.save(details);
    }

    public ResponseEntity<Wrapper<Data>> get(String username) {
        Details details = detailsDBService.findByUsername(username);
        LOGGER.info("DetailsService [account-management-service] get entity [{}]", details);

        return response(Message.SUCCESS_FIND_DETAILS_BY_USERNAME,
            List.of(detailsMapper.toDTO(details)));
    }

    public ResponseEntity<Wrapper<Data>> partiallyUpdate(String username,
        Map<String, Object> updates) {
        Details details = detailsDBService.findByUsername(username);

        updates.remove("username");
        Data data = objectMapper.convertValue(updates, Data.class);

        BeanUtils.copyProperties(data, details, getNullPropertyNames(data));

        detailsDBService.save(details);
        LOGGER.info("DetailsService [account-management-service] new entity [{}]", details);

        return response(Message.SUCCESS_PARTIALLY_UPDATE, List.of(detailsMapper.toDTO(details)));
    }

    public ResponseEntity<HttpStatus> delete(String username) {
        detailsDBService.deleteByUsername(username);
        kafkaProducerService.produce(Constants.DELETE_ACCOUNT_TOPIC, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private <E extends CommonDTO> ResponseEntity<Wrapper<E>> response(String message,
        List<E> payload) {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new Wrapper<>(status, message, LocalDateTime.now(), payload),
            status);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        return Arrays.stream(pds)
            .map(java.beans.PropertyDescriptor::getName)
            .filter(propertyName -> src.getPropertyValue(propertyName) == null)
            .toArray(String[]::new);
    }
}