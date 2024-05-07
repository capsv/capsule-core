package auth.service.dev.dtos.responses.token;

import auth.service.dev.dtos.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NakedPersonDTO extends CommonDTO {

    private long id;

    private String username;

    private String email;
}
