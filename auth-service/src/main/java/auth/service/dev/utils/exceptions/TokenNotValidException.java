package auth.service.dev.utils.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenNotValidException extends RuntimeException {

    public TokenNotValidException(String message) {
        super(message);
    }
}
