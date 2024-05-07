package auth.service.dev.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRegisterReqst {

    @NotBlank(message = "should be not blank")
    @Size(min = 4, max = 254, message = "size should be between 4 and 254")
    //UNIQUE
    private String username;

    @Email(message = "must match the email template")
    @NotBlank(message = "should be not blank")
    @Size(min = 4,max = 254, message = "size should be between 4 and 254")
    //UNIQUE
    private String email;

    @NotBlank(message = "password should be not blank")
    @Size(min = 4, max = 254, message = "password size should be between 4 and 254")
    private String password;

    @NotBlank(message = "password should be not blank")
    @Size(min = 4, max = 254, message = "password size should be between 4 and 254")
    private String confirmationPassword;
}
