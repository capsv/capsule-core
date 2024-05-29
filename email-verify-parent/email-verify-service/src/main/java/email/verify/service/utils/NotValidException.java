package email.verify.service.utils;

import email.verify.service.dtos.CommonDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotValidException extends RuntimeException {

    private List<? extends CommonDTO> errors;
}