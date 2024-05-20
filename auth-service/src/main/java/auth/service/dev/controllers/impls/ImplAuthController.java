package auth.service.dev.controllers.impls;

import auth.service.dev.controllers.interfaces.IAuthController;
import auth.service.dev.dtos.requests.PersonAuthReqst;
import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.dtos.requests.RefreshTokenReqst;
import auth.service.dev.dtos.requests.TokenReqst;
import auth.service.dev.dtos.responses.entities.Credentials;
import auth.service.dev.dtos.responses.tokens.RespWrapper;
import auth.service.dev.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Slf4j
public abstract class ImplAuthController implements IAuthController {

    private final AuthService authService;

    public ImplAuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<RespWrapper> register(PersonRegisterReqst reqst,
        BindingResult bindingResult) {
        log.info("START REGISTRATION USER '{}'", reqst.getUsername());
        return authService.register(reqst, bindingResult);
    }

    @Override
    public ResponseEntity<RespWrapper> authenticateByRefreshToken(RefreshTokenReqst reqst) {
        log.info("START AUTHENTICATION VIA REFRESH TOKEN");
        return authService.authenticateByRefreshToken(reqst);
    }

    @Override
    public ResponseEntity<RespWrapper> authenticate(PersonAuthReqst reqst,
        BindingResult bindingResult) {
        log.info("START AUTHENTICATION VIA LOGIN AND PASSWORD FOR - '{}'", reqst.getUsername());
        return authService.authenticate(reqst, bindingResult);
    }

    @Override
    public ResponseEntity<Boolean> validate(TokenReqst token) {
        log.info("START VALIDATION TOKEN VIA POST");
        return authService.validateToken(token);
    }

    @Override
    public ResponseEntity<Credentials> validate(String token) {
        log.info("START VALIDATION TOKEN VIA GET");
        return authService.validateToken(token);
    }
}