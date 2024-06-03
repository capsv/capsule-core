package email.verify.service.controllers.impls;

import email.verify.service.controllers.interfaces.IVerifyController;
import email.verify.service.dtos.requests.UserInfoReqst;
import email.verify.service.services.VerifyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/verify")
@AllArgsConstructor
public class ImplVerifyController implements IVerifyController {

    private final VerifyService verifyService;

    @Override
    public ResponseEntity<HttpStatus> post(UserInfoReqst info, BindingResult bindingResult) {
        return verifyService.verify(info, bindingResult);
    }

    @Override
    public ResponseEntity<HttpStatus> verify() {
        return null;
    }
}