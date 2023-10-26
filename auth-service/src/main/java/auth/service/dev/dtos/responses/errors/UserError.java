package auth.service.dev.dtos.responses.errors;

import auth.service.dev.dtos.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserError extends CommonDTO {

    private String error;
}
