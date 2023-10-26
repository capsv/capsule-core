package auth.service.dev.utils.validations;

import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.services.PeopleDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PersonUsernameValidation implements Validator {

    private final PeopleDBService peopleDBService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PersonRegisterReqst.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonRegisterReqst person=(PersonRegisterReqst) target;

        if(peopleDBService.getByUsername(person.getUsername()).isPresent())
            errors.rejectValue("username","","should be unique");
    }
}
