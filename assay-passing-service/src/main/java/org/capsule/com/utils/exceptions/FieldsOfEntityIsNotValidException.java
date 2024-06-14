package org.capsule.com.utils.exceptions;

import java.util.List;
import lombok.Getter;
import org.capsule.com.dtos.Error;

@Getter
public class FieldsOfEntityIsNotValidException extends RuntimeException {

    private final List<Error> errors;

    public FieldsOfEntityIsNotValidException(List<Error> errors) {
        this.errors = errors;
    }
}
