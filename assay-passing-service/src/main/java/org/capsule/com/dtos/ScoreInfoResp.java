package org.capsule.com.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.capsule.com.dtos.common.CommonDTO;

public record ScoreInfoResp(@JsonProperty("score") int score) implements CommonDTO {

}
