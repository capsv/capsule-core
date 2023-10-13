package dev.copavor.com.controllers;

import dev.copavor.com.controllers.impls.ImplAuthController;
import dev.copavor.com.services.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/auth")
public class AuthController extends ImplAuthController {

    public AuthController(AuthService authService) {
        super(authService);
    }
}
