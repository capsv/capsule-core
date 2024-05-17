package auth.service.dev.dtos.responses.tokens;

import auth.service.dev.dtos.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResp extends CommonDTO {

    private AccessToken accessToken;

    private NakedPersonDTO user;
}
