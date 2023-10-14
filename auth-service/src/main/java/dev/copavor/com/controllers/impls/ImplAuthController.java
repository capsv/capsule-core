package dev.copavor.com.controllers.impls;

import dev.copavor.com.controllers.interfaces.IAuthController;
import dev.copavor.com.dtos.requests.PersonAuthReqst;
import dev.copavor.com.dtos.requests.PersonRegisterReqst;
import dev.copavor.com.dtos.requests.TokenReqst;
import dev.copavor.com.dtos.responses.AuthResp;
import dev.copavor.com.dtos.responses.RespWrapper;
import dev.copavor.com.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Slf4j
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

    @Override
    public ResponseEntity<Boolean> validate(TokenReqst token) {
        log.info("--------------------\n+"+token.getToken()+"\n--------------------");
        return authService.validateToken(token);
    }
}
