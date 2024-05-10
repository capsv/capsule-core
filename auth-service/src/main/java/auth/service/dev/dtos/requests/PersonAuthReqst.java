package auth.service.dev.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonAuthReqst {

    @NotBlank(message = "should be not blank",groups = {})
    @Size(min = 4, max = 56, message = "size should be between 4 and 56")
    //NOT UNIQUE
    private String username;

    @NotBlank(message = "password should be not blank")
    @Size(min = 4, max = 56, message = "password size should be between 4 and 56")
    private String password;
}
