package org.capsule.com.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.capsule.com.dtos.CommonDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoReqst implements CommonDTO {

    @Email(message = "must match the email template")
    @NotBlank(message = "should be not blank")
    @Size(min = 4, max = 56, message = "size should be between 4 and 56")
    private String email;
}