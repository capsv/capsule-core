package auth.service.dev.controllers.interfaces;

import auth.service.dev.dtos.requests.PersonAuthReqst;
import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.dtos.requests.RefreshTokenReqst;
import auth.service.dev.dtos.requests.TokenReqst;
import auth.service.dev.dtos.responses.token.RespWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    @PostMapping("/register")
    @Operation(summary = "Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "User was register",
            content = {
                    @Content(mediaType = "application/json",schema = @Schema(implementation = RespWrapper.class))
            }),
            @ApiResponse(responseCode = "400",description = "Bad request")
    })
    ResponseEntity<RespWrapper> register(
            @RequestBody @Valid PersonRegisterReqst reqst, BindingResult bindingResult
    );

    @PostMapping("/token/authenticate")
    @Operation(summary = "Authentication by refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Authentication successful"),
            @ApiResponse(responseCode = "400",description = "Bad request (Token is not valid)"),
    })
    ResponseEntity<RespWrapper> authenticateByRefreshToken(@RequestBody RefreshTokenReqst reqst);


    @PostMapping("/authenticate")
    @Operation(summary = "Authentication by login and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Authentication successful"),
            @ApiResponse(responseCode = "400",description = "Bad request"),
            @ApiResponse(responseCode = "404",description = "Not found")
    })
    ResponseEntity<RespWrapper> authenticate(
            @RequestBody @Valid PersonAuthReqst reqst, BindingResult bindingResult
    );

    @PostMapping("/validate")
    @Operation(summary = "Validate token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Validation is success (return 'true' or 'false'"),
    })
    ResponseEntity<Boolean> validate(
            @RequestBody TokenReqst token
    );
}

