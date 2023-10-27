package auth.service.dev.controllers;

import auth.service.dev.controllers.impls.ImplAuthController;
import auth.service.dev.services.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/api/v1.0")
public class AuthController extends ImplAuthController {

    public AuthController(AuthService authService) {
        super(authService);
    }
}
