package org.capsule.com.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Letter(@JsonProperty("username") String username, @JsonProperty("email") String email,
                     @JsonProperty("code") int code) {

}
