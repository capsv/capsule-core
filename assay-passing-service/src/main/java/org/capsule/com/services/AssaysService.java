package org.capsule.com.services;

import java.time.LocalDateTime;
import java.util.List;
import org.capsule.com.configs.Constants;
import org.capsule.com.dtos.AssayReqst;
import org.capsule.com.dtos.Error;
import org.capsule.com.dtos.RatingInfoResp;
import org.capsule.com.dtos.Wrapper;
import org.capsule.com.utils.exceptions.FieldsOfEntityIsNotValidException;
import org.capsule.com.utils.tools.GenerateRatingTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class AssaysService {

    public ResponseEntity<Wrapper<RatingInfoResp>> pass(AssayReqst assayReqst,
        BindingResult bindingResult) {
        validate(bindingResult);

        int level = GenerateRatingTool.generate(assayReqst.assay());

        return response(level);
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

    private ResponseEntity<Wrapper<RatingInfoResp>> response(int level) {
        var status = HttpStatus.OK;
        return new ResponseEntity<>(
            new Wrapper<>(status, Constants.SUCCESS_MESSAGE, LocalDateTime.now(),
                List.of(new RatingInfoResp(level))), status);
    }
}
