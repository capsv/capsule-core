package org.capsule.com.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.capsule.com.dtos.common.CommonDTO;

public record Data(@JsonProperty("username") String username,

                   @JsonProperty("firstName") @NotBlank(message = "should be not blank")
                   @Size(min = 4, max = 128, message = "should be between 4 and 128") String firstName,

                   @JsonProperty("secondName") @NotBlank(message = "should be not blank")
                   @Size(min = 4, max = 128, message = "should be between 4 and 128") String secondName,

                   @JsonProperty("age") @Min(value = 0) @Max(value = 128) Integer age) implements CommonDTO {

}
