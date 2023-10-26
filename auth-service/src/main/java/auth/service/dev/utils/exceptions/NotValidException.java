package auth.service.dev.utils.exceptions;

import auth.service.dev.dtos.responses.errors.FieldError;
import lombok.Getter;

import java.util.List;

@Getter
public class NotValidException extends RuntimeException{

    private final List<FieldError> errors;

    public NotValidException(List<FieldError> errors){
        this.errors=errors;
    }
}
