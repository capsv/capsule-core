package auth.service.dev.dtos.responses.tokens;

import auth.service.dev.dtos.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NakedPersonDTO extends CommonDTO {

    private String username;

    //TODO authorities
}