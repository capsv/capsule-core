package email.verify.service.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CodeConfirmReqst {

    @NotBlank(message = "should be not blank")
    @Size(min = 4, max = 56, message = "size should be between 4 and 56")
    private String username;

    @NotBlank(message = "should be not blank")
    private int code;
}
