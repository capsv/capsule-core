package dev.copavor.com.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.copavor.com.common.Status;
import dev.copavor.com.dtos.CommonDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespWrapper{

    private Status status;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private List<? extends CommonDTO> payload;

}