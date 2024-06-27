package org.capsule.com.controllers.impls;

import lombok.AllArgsConstructor;
import org.capsule.com.controllers.interfaces.IVerifyController;
import org.capsule.com.dtos.requests.CodeConfirmReqst;
import org.capsule.com.dtos.requests.UserInfoReqst;
import org.capsule.com.services.VerifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/verifications")
@AllArgsConstructor
public class VerifyControllerImpl implements IVerifyController {

    private final VerifyService verifyService;

    @Override
    public ResponseEntity<HttpStatus> request(String token, UserInfoReqst info,
        BindingResult bindingResult) {
        return verifyService.request(token, info, bindingResult);
    }

    @Override
    public ResponseEntity<HttpStatus> confirm(String token, CodeConfirmReqst code,
        BindingResult bindingResult) {
        return verifyService.confirm(token, code, bindingResult);
    }
}