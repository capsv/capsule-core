package org.capsule.com.services;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.capsule.com.configs.Constants;
import org.capsule.com.dtos.AssayReqst;
import org.capsule.com.dtos.Error;
import org.capsule.com.dtos.ScoreInfoResp;
import org.capsule.com.dtos.Wrapper;
import org.capsule.com.services.producers.KafkaJsonProducer;
import org.capsule.com.services.producers.KafkaStringProducer;
import org.capsule.com.utils.exceptions.FieldsOfEntityIsNotValidException;
import org.capsule.com.utils.tools.GenerateScoreTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class AssaysService {

    private final Logger LOGGER = LoggerFactory.getLogger(AssaysService.class);
    private final DecodeJWTService decodeJWTService;
    private final KafkaStringProducer kafkaStringProducer;
    private final KafkaJsonProducer kafkaJsonProducer;

    public ResponseEntity<Wrapper<ScoreInfoResp>> pass(String token, AssayReqst request,
        BindingResult bindingResult) {
        validate(bindingResult);

        String username = decodeJWTService.extractUsername(token.substring(7));
        int score = GenerateScoreTool.generate(request.assay());

        kafkaStringProducer.produce(Constants.SUBMIT_ASSAY_PASSED_STATUS_TOPIC, username);
        kafkaJsonProducer.produce(Constants.SCORE_AFTER_ASSAY_TOPIC,
            new AssaysService.AssayResult(username, score));

        LOGGER.info("AssaysService [assay-passing-service] the score for [{}]", username);

        return response(score);
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Error> errors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> new Error(error.getField(), error.getDefaultMessage()))
                .toList();

            throw new FieldsOfEntityIsNotValidException(errors);
        }
    }

    private ResponseEntity<Wrapper<ScoreInfoResp>> response(int level) {
        var status = HttpStatus.OK;
        return new ResponseEntity<>(
            new Wrapper<>(status, Constants.SUCCESS_MESSAGE, LocalDateTime.now(),
                List.of(new ScoreInfoResp(level))), status);
    }

    private record AssayResult(String username, int level) { }
}
