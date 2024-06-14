package org.capsule.com.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import org.capsule.com.dtos.common.CommonDTO;
import org.springframework.http.HttpStatus;

public record Wrapper<E extends CommonDTO>(@JsonProperty("status") HttpStatus status,
                                           @JsonProperty("message") String message,
                                           @JsonProperty("time") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time,
                                           @JsonProperty("payload") List<E> payload) implements
    CommonDTO {

}