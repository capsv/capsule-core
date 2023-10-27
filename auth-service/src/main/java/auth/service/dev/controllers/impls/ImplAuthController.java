package auth.service.dev.controllers.impls;

import auth.service.dev.dtos.requests.PersonAuthReqst;
import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.dtos.requests.RefreshTokenReqst;
import auth.service.dev.dtos.requests.TokenReqst;
import auth.service.dev.dtos.responses.token.RespWrapper;
import auth.service.dev.services.AuthService;
import auth.service.dev.controllers.interfaces.IAuthController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Slf4j
public abstract class ImplAuthController implements IAuthController {

    private final AuthService authService;

    public ImplAuthController(AuthService authService){
        this.authService=authService;
    }

    @Override
    public ResponseEntity<RespWrapper> register(
            PersonRegisterReqst reqst, BindingResult bindingResult) {
        return authService.register(reqst,bindingResult);
    }

    @Override
    public ResponseEntity<RespWrapper> authenticateByRefreshToken(RefreshTokenReqst reqst) {
        return authService.authenticateByRefreshToken(reqst);
    }

    @Override
    public ResponseEntity<RespWrapper> authenticate(
            PersonAuthReqst reqst, BindingResult bindingResult) {
        return authService.authenticate(reqst, bindingResult);
    }

    @Override
    public ResponseEntity<Boolean> validate(TokenReqst token) {
        return authService.validateToken(token);
    }
}
