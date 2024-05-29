package email.verify.service.services;

import email.verify.service.dtos.errors.WrongField;
import email.verify.service.dtos.requests.UserInfoReqst;
import email.verify.service.utils.NotValidException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class VerifyService {

    public ResponseEntity<HttpStatus> verify(UserInfoReqst info, BindingResult bindingResult) {
        validate(bindingResult);


        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<WrongField> wrongFields = new ArrayList<>();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                wrongFields.add(
                    WrongField.builder().field(error.getField()).error(error.getDefaultMessage())
                        .build());
            }

            throw new NotValidException(wrongFields);
        }
    }
}
