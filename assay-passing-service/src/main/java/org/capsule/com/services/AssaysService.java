package org.capsule.com.services;

import jakarta.validation.ValidationException;
import java.util.List;
import org.capsule.com.dtos.AssayReqst;
import org.capsule.com.dtos.Error;
import org.capsule.com.dtos.RatingInfoResp;
import org.capsule.com.dtos.Wrapper;
import org.capsule.com.utils.FieldsOfEntityIsNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class AssaysService {

    public ResponseEntity<Wrapper<RatingInfoResp>> pass(AssayReqst assayReqst,
        BindingResult bindingResult) {
        validate(bindingResult);



        return null;
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
}
