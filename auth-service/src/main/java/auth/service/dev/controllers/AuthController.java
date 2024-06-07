package auth.service.dev.controllers;

import auth.service.dev.controllers.impls.AuthControllerImpl;
import auth.service.dev.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Auth-Controller", description = "Auth API")
public class AuthController extends AuthControllerImpl {

    public AuthController(AuthService authService) {
        super(authService);
    }
}