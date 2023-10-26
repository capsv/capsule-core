package auth.service.dev.dtos.responses.errors;

import auth.service.dev.dtos.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldError extends CommonDTO {

    private String field;

    private String error;
}
