package auth.service.dev.utils.exceptions;

import auth.service.dev.dtos.responses.errors.CustomFieldError;
import lombok.Getter;

import java.util.List;

@Getter
public class NotValidException extends RuntimeException{

    private final List<CustomFieldError> errors;

    public NotValidException(List<CustomFieldError> errors){
        this.errors=errors;
    }
}
