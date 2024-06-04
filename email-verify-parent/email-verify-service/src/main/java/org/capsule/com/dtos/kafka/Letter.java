package org.capsule.com.dtos.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Letter(@JsonProperty("username") String username, @JsonProperty("email") String email,
                     @JsonProperty("code") int code) {

}