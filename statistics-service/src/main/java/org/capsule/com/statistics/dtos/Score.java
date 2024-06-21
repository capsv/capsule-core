package org.capsule.com.statistics.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Score (@JsonProperty("username") String username, @JsonProperty("score") int score) {

}
