package auth.service.dev.utils.exceptions;

public class NotAuthenticateException extends RuntimeException {

    public NotAuthenticateException(String message) {
        super(message);
    }
}
