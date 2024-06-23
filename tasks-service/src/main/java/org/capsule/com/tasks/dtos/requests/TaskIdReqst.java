package org.capsule.com.tasks.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.capsule.com.tasks.dtos.CoDto;

public record TaskIdReqst(
    @JsonProperty("taskId") @NotNull(message = "should be not null") long taskId) implements CoDto {

}
