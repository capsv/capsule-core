package dev.copavor.com.dtos.responses.errors;

import dev.copavor.com.dtos.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserError extends CommonDTO {

    private String error;
}
