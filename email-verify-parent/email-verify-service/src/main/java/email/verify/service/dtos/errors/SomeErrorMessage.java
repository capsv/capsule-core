package email.verify.service.dtos.errors;

import email.verify.service.dtos.CommonDTO;
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
public class SomeErrorMessage implements CommonDTO {

    private String message;
}
