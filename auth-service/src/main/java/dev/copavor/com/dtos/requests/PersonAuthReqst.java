package dev.copavor.com.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonAuthReqst {

    @NotBlank(message = "should be not blank")
    @Size(min = 4, max = 254, message = "size should be between 4 and 254")
    //NOT UNIQUE
    private String username;

    @NotBlank(message = "password should be not blank")
    @Size(min = 4, max = 254, message = "password size should be between 4 and 254")
    private String password;
}
