package org.capsule.com.utils.exceptions;

import java.util.List;
import lombok.Getter;
import org.capsule.com.dtos.CommonDTO;

@Getter
public class NotValidException extends RuntimeException {

    private List<? extends CommonDTO> errors;

    public NotValidException(String message, List<? extends CommonDTO> errors) {
        super(message);
        this.errors = errors;
    }
}