package auth.service.dev.common;

import lombok.Getter;

@Getter
public enum Status {

    SUCCESS, AUTHENTICATED, BAD_REQUEST, NOT_VALID, NOT_AUTHENTICATED;
}