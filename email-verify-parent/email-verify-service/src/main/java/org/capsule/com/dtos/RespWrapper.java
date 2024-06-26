package org.capsule.com.dtos;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespWrapper {

    private HttpStatus status;

    private String message;

    private LocalDateTime time;

    private List<? extends CommonDTO> payload;
}