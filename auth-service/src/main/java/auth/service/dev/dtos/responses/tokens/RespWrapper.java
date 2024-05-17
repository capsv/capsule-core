package auth.service.dev.dtos.responses.tokens;

import com.fasterxml.jackson.annotation.JsonFormat;
import auth.service.dev.common.Status;
import auth.service.dev.dtos.CommonDTO;
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