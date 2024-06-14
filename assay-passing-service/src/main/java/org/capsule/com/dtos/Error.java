package org.capsule.com.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.capsule.com.dtos.common.CommonDTO;

public record Error(@JsonProperty("field") String field,
                    @JsonProperty("message") String message) implements CommonDTO {

}
