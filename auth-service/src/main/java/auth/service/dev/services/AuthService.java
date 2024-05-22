package auth.service.dev.services;

import auth.service.dev.common.Constants;
import auth.service.dev.common.Role;
import auth.service.dev.common.Status;
import auth.service.dev.dtos.requests.PersonAuthReqst;
import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.dtos.requests.RefreshTokenReqst;
import auth.service.dev.dtos.requests.TokenReqst;
import auth.service.dev.dtos.responses.entities.Credentials;
import auth.service.dev.dtos.responses.errors.CustomFieldError;
import auth.service.dev.dtos.responses.tokens.ResponseWrapper;
import auth.service.dev.dtos.responses.tokens.Token;
import auth.service.dev.dtos.responses.tokens.TokensPayloadResp;
import auth.service.dev.models.Person;
import auth.service.dev.security.JwtService;
import auth.service.dev.security.PersonDetails;
import auth.service.dev.security.PersonDetailsService;
import auth.service.dev.utils.exceptions.NotFoundException;
import auth.service.dev.utils.exceptions.NotValidException;
import auth.service.dev.utils.exceptions.TokenNotValidException;
import auth.service.dev.utils.validations.EmailValidation;
import auth.service.dev.utils.validations.PasswordConfirmationValidation;
import auth.service.dev.utils.validations.UsernameValidation;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@Slf4j
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PeopleDBService peopleDBService;
    private final UsernameValidation usernameValidation;
    private final EmailValidation emailValidation;
    private final PasswordConfirmationValidation passwordConfirmationValidation;
    private final PersonDetailsService personDetailsService;

    public AuthService(JwtService jwtService, PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager, PeopleDBService peopleDBService,
        UsernameValidation usernameValidation, EmailValidation emailValidation,
        PasswordConfirmationValidation passwordConfirmationValidation,
        PersonDetailsService personDetailsService) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.peopleDBService = peopleDBService;
        this.usernameValidation = usernameValidation;
        this.emailValidation = emailValidation;
        this.passwordConfirmationValidation = passwordConfirmationValidation;
        this.personDetailsService = personDetailsService;
    }

    public ResponseEntity<ResponseWrapper> register(PersonRegisterReqst request,
        BindingResult bindingResult) {

        validate(request, bindingResult);
        var person = createEntityByRequest(request);

        peopleDBService.save(person);

        var accessToken = jwtService.generateAccessToken(new PersonDetails(person));
        var refreshToken = jwtService.generateRefreshToken(new PersonDetails(person));

        return response(Status.SUCCESS, Constants.REGISTRATION_SUCCESS, accessToken, refreshToken, person);
    }

    private Person createEntityByRequest(PersonRegisterReqst request) {
        return Person.builder().username(request.getUsername()).email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
    }

    private ResponseEntity<ResponseWrapper> response(Status status, String message, String access,
        String refresh, Person person) {
        return ResponseEntity.ok(
            ResponseWrapper.builder().status(status).message(message).time(LocalDateTime.now())
                .payload(
                    List.of(TokensPayloadResp.builder()
                        .access(
                            Token.builder().token(access).iat(LocalDateTime.now())
                                .exp(extractExpDateFromToken(access)).build()
                        )
                        .refresh(
                            Token.builder().token(refresh).iat(LocalDateTime.now())
                                .exp(extractExpDateFromToken(refresh)).build()
                        )
                        .data(
                            Credentials.builder().username(person.getUsername()).build()
                        ).build()
                    )).build()
        );
    }

    private LocalDateTime extractExpDateFromToken(String token) {
        return toLocalDateTime(jwtService.extractExpirationDateFromToken(token));
    }

    private LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private void validate(PersonRegisterReqst request, BindingResult bindingResult) {
        usernameValidation.validate(request, bindingResult);
        emailValidation.validate(request, bindingResult);
        passwordConfirmationValidation.validate(request, bindingResult);
        validateResults(bindingResult);
    }

    private void validateResults(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<CustomFieldError> errors = new ArrayList<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError error : fieldErrors) {
                errors.add(new CustomFieldError(error.getField(), error.getDefaultMessage()));
            }
            throw new NotValidException(errors);
        }
    }

    public ResponseEntity<ResponseWrapper> authenticateByRefreshToken(RefreshTokenReqst reqst) {

        String refreshToken = reqst.getToken();

        if (!jwtService.validateJwt(refreshToken)) {
            log.info("TOKEN IS NOT VALID");
            throw new TokenNotValidException();
        }

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = personDetailsService.loadUserByUsername(username);
        var person = peopleDBService.getByUsername(username).orElseThrow(
            () -> new NotFoundException("user with username: " + username + " not found"));

        PersonDetails personDetails = (PersonDetails) userDetails;
        log.info("AUTHENTICATE BY REFRESH TOKEN: {}", personDetails.toString());

        log.info("ALL GOOD -> STARTING GENERATING ACCESS TOKEN");
        var accessToken = jwtService.generateAccessToken(userDetails);

        return ResponseEntity.ok(null);
    }


    public ResponseEntity<ResponseWrapper> authenticate(PersonAuthReqst reqst,
        BindingResult bindingResult) {

        validateResults(bindingResult);

        var person = peopleDBService.getByUsername(reqst.getUsername()).orElseThrow(
            () -> new NotFoundException(
                "user with username '" + reqst.getUsername() + "' not found"));

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(reqst.getUsername(), reqst.getPassword()));

        var accessToken = jwtService.generateAccessToken(new PersonDetails(person));
        var refreshToken = jwtService.generateRefreshToken(new PersonDetails(person));

        return ResponseEntity.ok(null);
    }

    public ResponseEntity<Boolean> validateToken(TokenReqst token) {
        boolean isValid = jwtService.validateJwt(token.getToken());
        log.info(isValid ? "VALID" : "INVALID");
        return ResponseEntity.ok(isValid);
    }

    public ResponseEntity<Credentials> validateToken(String token) {
        boolean isValid = jwtService.validateJwt(token);
        return isValid ? ResponseEntity.ok()
            .body(Credentials.builder().username(jwtService.extractUsername(token)).build())
            : ResponseEntity.ok(Credentials.builder().build());
    }

}
