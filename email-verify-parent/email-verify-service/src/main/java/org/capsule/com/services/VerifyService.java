package org.capsule.com.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.capsule.com.configs.Constants;
import org.capsule.com.configs.Message;
import org.capsule.com.dtos.errors.WrongField;
import org.capsule.com.dtos.requests.CodeConfirmReqst;
import org.capsule.com.dtos.requests.UserInfoReqst;
import org.capsule.com.models.Verify;
import org.capsule.com.services.producers.KafkaJsonProducerService;
import org.capsule.com.services.producers.KafkaStringProducerService;
import org.capsule.com.utils.exceptions.NotValidException;
import org.capsule.com.utils.tools.CodeGeneratorTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class VerifyService {

    private final VerifyDBService verifyDBService;
    private final CodeGeneratorTool codeGeneratorTool;
    private final KafkaJsonProducerService kafkaJsonProducerService;
    private final KafkaStringProducerService kafkaStringProducerService;

    public ResponseEntity<HttpStatus> request(String token, UserInfoReqst info,
        BindingResult bindingResult) {
        validate(bindingResult);

        Verify verify = createEntity(info);
        verifyDBService.save(verify);
        kafkaJsonProducerService.produce(Constants.LETTERS_WITH_CODE_TOPIC, verify);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> confirm(String token, CodeConfirmReqst code,
        BindingResult bindingResult) {
        validate(bindingResult);

        String username = code.getUsername();
        Verify verify = verifyDBService.findByUsername(username);
        if (verify.getCode() == code.getCode()) {
            verifyDBService.deleteAllByUsername(username);
            kafkaStringProducerService.produce(Constants.SUBMIT_VERIFY_STATUS_TOPIC, username);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Verify createEntity(UserInfoReqst info) {
        String username = info.getUsername();
        String email = info.getEmail();
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
}