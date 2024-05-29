package email.verify.service.services;

import email.verify.service.dtos.errors.WrongField;
import email.verify.service.dtos.requests.UserInfoReqst;
import email.verify.service.models.Verify;
import email.verify.service.services.producers.VerifyProducer;
import email.verify.service.utils.exceptions.NotValidException;
import email.verify.service.utils.tools.CodeGeneratorTool;
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
    private final VerifyProducer verifyProducer;

    public ResponseEntity<HttpStatus> verify(UserInfoReqst info, BindingResult bindingResult) {
        validate(bindingResult);

        Verify verify = createEntity(info);
        verifyDBService.save(verify);
        verifyProducer.produce(verify);

        return ResponseEntity.ok(HttpStatus.OK);
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
