package org.capsule.com.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.capsule.com.dtos.common.CommonDTO;

public record RatingInfoResp (@JsonProperty("level") int level) implements CommonDTO {

}
