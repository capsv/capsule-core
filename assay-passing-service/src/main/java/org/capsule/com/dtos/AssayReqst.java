package org.capsule.com.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.capsule.com.dtos.common.CommonDTO;

public record AssayReqst(
    @JsonProperty("assay") @NotBlank(message = "should be not blank") String assay) implements
    CommonDTO {

}
