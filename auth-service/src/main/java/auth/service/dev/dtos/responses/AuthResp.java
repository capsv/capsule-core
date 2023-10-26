package auth.service.dev.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import auth.service.dev.dtos.CommonDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResp extends CommonDTO {

    private String token;

    //Время, когда токен был выпущен (issued at)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime iat;

    //Время истечения токена
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime exp;

    private NakedPersonDTO user;
}
