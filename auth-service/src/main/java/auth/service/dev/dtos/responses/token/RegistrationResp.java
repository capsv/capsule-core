package auth.service.dev.dtos.responses.token;

import auth.service.dev.dtos.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResp extends CommonDTO {

    private AccessToken accessToken;

    private RefreshToken refreshToken;

    private NakedPersonDTO user;
}
