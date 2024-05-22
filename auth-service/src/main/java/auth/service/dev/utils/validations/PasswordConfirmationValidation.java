package auth.service.dev.utils.validations;

import auth.service.dev.dtos.requests.PersonRegisterReqst;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class PasswordConfirmationValidation {

    public void validate(PersonRegisterReqst request, BindingResult bindingResult) {
        if (!request.getPassword().equals(request.getConfirmationPassword())) {
            bindingResult.rejectValue("confirmationPassword", "",
                "the confirmation password must match the password");
        }
    }
}
