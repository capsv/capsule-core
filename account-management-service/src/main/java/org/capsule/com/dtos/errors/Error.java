package org.capsule.com.dtos.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.capsule.com.dtos.common.CommonDTO;

public record Error(@JsonProperty("error") String error) implements CommonDTO {

}