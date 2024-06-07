package org.capsule.com.services;

import org.capsule.com.dtos.errors.WrongField;
import org.capsule.com.models.Verify;
import org.capsule.com.services.producers.KafkaJsonProducerService;
import org.capsule.com.services.producers.KafkaStringProducerService;
import org.capsule.com.utils.exceptions.NotValidException;
import org.capsule.com.utils.tools.CodeGeneratorTool;
import org.capsule.com.configs.Constants;
import org.capsule.com.dtos.requests.CodeConfirmReqst;
import org.capsule.com.dtos.requests.UserInfoReqst;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

    public ResponseEntity<HttpStatus> request(UserInfoReqst info, BindingResult bindingResult) {
        validate(bindingResult);

        Verify verify = createEntity(info);
        verifyDBService.save(verify);
        kafkaJsonProducerService.produce(verify, Constants.LETTERS_WITH_CODE_TOPIC);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> confirm(CodeConfirmReqst codeConfirmReqst,
        BindingResult bindingResult) {
        validate(bindingResult);

        String username = codeConfirmReqst.getUsername();
        Verify verify = verifyDBService.findByUsername(username);
        if (verify.getCode() == codeConfirmReqst.getCode()) {
            verifyDBService.deleteAllByUsername(username);
            kafkaStringProducerService.produce(username, Constants.SUBMIT_VERIFY_STATUS_TOPIC);
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
        return Verify.builder().username(username).email(email).code(code).createdAt(createdAt)
            .build();
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<WrongField> wrongFields = bindingResult.getFieldErrors().stream().map(
                error -> WrongField.builder().field(error.getField())
                    .error(error.getDefaultMessage()).build()).collect(Collectors.toList());

            throw new NotValidException(wrongFields);
        }
    }
}