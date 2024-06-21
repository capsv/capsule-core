package org.capsule.com.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.capsule.com.configs.Constants;
import org.capsule.com.configs.Message;
import org.capsule.com.dtos.errors.SomeErrorMessage;
import org.capsule.com.dtos.errors.WrongField;
import org.capsule.com.dtos.requests.CodeConfirmReqst;
import org.capsule.com.dtos.requests.UserInfoReqst;
import org.capsule.com.models.Verify;
import org.capsule.com.services.producers.KafkaJsonProducerService;
import org.capsule.com.services.producers.KafkaStringProducerService;
import org.capsule.com.utils.exceptions.NotValidException;
import org.capsule.com.utils.tools.CodeGeneratorTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class VerifyService {

    private final Logger LOGGER = LoggerFactory.getLogger(VerifyService.class);
    private final VerifyDBService verifyDBService;
    private final CodeGeneratorTool codeGeneratorTool;
    private final DecodeJWTService decodeJWTService;
    private final KafkaJsonProducerService kafkaJsonProducerService;
    private final KafkaStringProducerService kafkaStringProducerService;

    public ResponseEntity<HttpStatus> request(String token, UserInfoReqst info,
        BindingResult bindingResult) {
        validate(bindingResult);

        String username = extractUsernameFromToken(token);
        Verify verify = createEntity(username, info.getEmail());
        verifyDBService.save(verify);
        kafkaJsonProducerService.produce(Constants.LETTERS_WITH_CODE_TOPIC, verify);

        LOGGER.info(
            "VerifyService [email-verify-service] the verification request has been accepted for [{}] to [{}]",
            username, info.getEmail());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> confirm(String token, CodeConfirmReqst code,
        BindingResult bindingResult) {
        validate(bindingResult);

        String username = extractUsernameFromToken(token);
        Verify verify = verifyDBService.findByUsername(username);
        if (verify.getCode() == code.getCode()
            && isWithinTwoMinutes(LocalDateTime.now(), verify.getCreatedAt())) {
            kafkaStringProducerService.produce(Constants.SUBMIT_VERIFY_STATUS_TOPIC, username);
            verifyDBService.deleteAllByUsername(username);
            LOGGER.info("VerifyService [email-verify-service] successful verification for [{}]", username);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            LOGGER.info("VerifyService [email-verify-service] failed verification for [{}]", username);
            throw new NotValidException("the code is already invalid",
                List.of(new SomeErrorMessage("the code is already invalid")));
        }
    }

    private String extractUsernameFromToken(String token) {
        try {
            return decodeJWTService.extractUsername(token.substring(7));
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private Verify createEntity(String username, String email) {
        int code = codeGeneratorTool.generate();
        LocalDateTime createdAt = LocalDateTime.now();
        return Verify.builder()
            .username(username)
            .email(email)
            .code(code)
            .createdAt(createdAt)
            .build();
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<WrongField> wrongFields = bindingResult.getFieldErrors()
                .stream()
                .map(error -> WrongField.builder()
                    .field(error.getField())
                    .error(error.getDefaultMessage())
                    .build())
                .collect(Collectors.toList());

            throw new NotValidException(Message.NOT_VALID_FIELDS_MSG, wrongFields);
        }
    }

    private boolean isWithinTwoMinutes(LocalDateTime first, LocalDateTime second) {
        Duration duration = Duration.between(first, second);
        return Math.abs(duration.toSeconds()) < 120;
    }
}