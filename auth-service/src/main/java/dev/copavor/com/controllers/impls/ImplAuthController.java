package dev.copavor.com.controllers.impls;

import dev.copavor.com.controllers.interfaces.IAuthController;
import dev.copavor.com.dtos.requests.PersonAuthReqst;
import dev.copavor.com.dtos.requests.PersonRegisterReqst;
import dev.copavor.com.dtos.responses.AuthResp;
import dev.copavor.com.dtos.responses.RespWrapper;
import dev.copavor.com.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
public abstract class ImplAuthController implements IAuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<RespWrapper> register(
            PersonRegisterReqst reqst, BindingResult bindingResult) {
        return authService.register(reqst,bindingResult);
    }

    @Override
    public ResponseEntity<RespWrapper> authenticate(
            PersonAuthReqst reqst, BindingResult bindingResult) {
        return authService.authenticate(reqst, bindingResult);
    }
}
