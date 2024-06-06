package auth.service.dev.utils.exceptions;

import auth.service.dev.dtos.responses.errors.CustomFieldError;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class NotValidException extends RuntimeException {

    private List<CustomFieldError> errors = new ArrayList<>();

    public NotValidException(List<CustomFieldError> errors) {
        this.errors = errors;
    }

    public NotValidException(String message) {
        super(message);
    }
}