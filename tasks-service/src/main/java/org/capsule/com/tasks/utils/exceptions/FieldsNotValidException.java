package org.capsule.com.tasks.utils.exceptions;

import java.util.List;
import lombok.Getter;
import org.capsule.com.tasks.dtos.CoDto;

@Getter
public class FieldsNotValidException extends RuntimeException {

    private final List<? extends CoDto> errors;

    public FieldsNotValidException(List<? extends CoDto> errors) {
        this.errors = errors;
    }
}
