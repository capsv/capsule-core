package auth.service.dev.common;

import lombok.Getter;

@Getter
public enum Status {

    SUCCESS, AUTHENTICATED, BAD_REQUEST, NOT_FOUND, NOT_VALID, NOT_AUTHENTICATED, INTERNAL_SERVER_ERROR;
}