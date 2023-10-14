package dev.copavor.com.controllers.interfaces;

import dev.copavor.com.dtos.requests.PersonAuthReqst;
import dev.copavor.com.dtos.requests.PersonRegisterReqst;
import dev.copavor.com.dtos.requests.TokenReqst;
import dev.copavor.com.dtos.responses.AuthResp;
import dev.copavor.com.dtos.responses.RespWrapper;
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