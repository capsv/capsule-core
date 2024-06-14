package org.capsule.com.utils.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private String field;

    private String error;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String field, String error) {
        super(message);
        this.field = field;
        this.error = error;
    }
}
