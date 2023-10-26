package auth.service.dev.controllers.interfaces;

import auth.service.dev.dtos.requests.PersonAuthReqst;
import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.dtos.requests.TokenReqst;
import auth.service.dev.dtos.responses.RespWrapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    @PostMapping("/register")
    ResponseEntity<RespWrapper> register(
            @RequestBody @Valid PersonRegisterReqst reqst, BindingResult bindingResult
            );

    @PostMapping("/authenticate")
    ResponseEntity<RespWrapper> authenticate(
            @RequestBody @Valid PersonAuthReqst reqst, BindingResult bindingResult
    );

    @PostMapping("/validate")
    ResponseEntity<Boolean> validate(
            @RequestBody TokenReqst token
            );

}