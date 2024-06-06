package auth.service.dev.controllers.impls;

import auth.service.dev.controllers.interfaces.IAuthController;
import auth.service.dev.dtos.requests.PersonAuthReqst;
import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.dtos.requests.RefreshTokenReqst;
import auth.service.dev.dtos.requests.TokenReqst;
import auth.service.dev.dtos.responses.entities.Credentials;
import auth.service.dev.dtos.responses.tokens.ResponseWrapper;
import auth.service.dev.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public abstract class ImplAuthController implements IAuthController {

    private final AuthService authService;

    public ImplAuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<ResponseWrapper> register(PersonRegisterReqst reqst,
        BindingResult bindingResult) {
        return authService.register(reqst, bindingResult);
    }

    @Override
    public ResponseEntity<ResponseWrapper> authenticateByRefreshToken(RefreshTokenReqst reqst) {
        return authService.authenticateByRefreshToken(reqst);
    }

    @Override
    public ResponseEntity<ResponseWrapper> authenticate(PersonAuthReqst reqst,
        BindingResult bindingResult) {
        return authService.authenticate(reqst, bindingResult);
    }

    @Override
    public ResponseEntity<Boolean> validate(TokenReqst token) {
        return authService.validateToken(token);
    }

    @Override
    public ResponseEntity<Credentials> validate(String token) {
        return authService.validateToken(token);
    }
}