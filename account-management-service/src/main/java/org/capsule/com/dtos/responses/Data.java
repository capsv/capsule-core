package org.capsule.com.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.capsule.com.dtos.common.CommonDTO;

public record Data(@JsonProperty("username") String username,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("secondName") String secondName,
                   @JsonProperty("age") Integer age) implements CommonDTO {

}
