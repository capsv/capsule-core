package org.capsule.com.controllers.impls;

import org.capsule.com.controllers.interfaces.IVerifyController;
import org.capsule.com.services.VerifyService;
import org.capsule.com.dtos.requests.CodeConfirmReqst;
import org.capsule.com.dtos.requests.UserInfoReqst;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email/verify")
@AllArgsConstructor
public class ImplVerifyController implements IVerifyController {

    private final VerifyService verifyService;

    @Override
    public ResponseEntity<HttpStatus> request(UserInfoReqst info, BindingResult bindingResult) {
        return verifyService.request(info, bindingResult);
    }

    @Override
    public ResponseEntity<HttpStatus> confirm(CodeConfirmReqst codeConfirmReqst,
        BindingResult bindingResult) {
        return verifyService.confirm(codeConfirmReqst, bindingResult);
    }
}