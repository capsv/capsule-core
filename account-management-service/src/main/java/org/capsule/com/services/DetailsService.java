package org.capsule.com.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.capsule.com.dtos.common.CommonDTO;
import org.capsule.com.dtos.responses.Data;
import org.capsule.com.dtos.responses.Wrapper;
import org.capsule.com.models.Details;
import org.capsule.com.utils.mappers.DetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(DetailsService.class);
    private final GenerateFirstSecondNameService generateFirstSecondNameService;
    private final DetailsDBService detailsDBService;
    //private final DetailsMapper detailsMapper;

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
        String message = "data by username";
      //  detailsMapper.toDTO(details)
        return response(HttpStatus.OK, message, List.of());
    }

    private <E extends CommonDTO> ResponseEntity<Wrapper<E>> response(HttpStatus status,
        String message, List<E> payload) {

        return new ResponseEntity<>(new Wrapper<>(status, message, LocalDateTime.now(), payload),
            status);
    }
}
