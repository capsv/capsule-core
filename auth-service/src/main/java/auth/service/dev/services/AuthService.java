package auth.service.dev.services;

import auth.service.dev.common.Constants;
import auth.service.dev.common.Role;
import auth.service.dev.common.Status;
import auth.service.dev.configs.Topics;
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
import auth.service.dev.services.producers.KafkaProducerService;
import auth.service.dev.utils.exceptions.NotAuthenticateException;
import auth.service.dev.utils.exceptions.NotFoundException;
import auth.service.dev.utils.exceptions.NotValidException;
import auth.service.dev.utils.exceptions.TokenNotValidException;
import auth.service.dev.utils.validations.EmailValidation;
import auth.service.dev.utils.validations.PasswordConfirmationValidation;
import auth.service.dev.utils.validations.UsernameValidation;
import com.auth0.jwt.exceptions.JWTDecodeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String NOT_AUTHENTICATE_MESSAGE = """
        some problem with authentication (check username or password)""";
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PeopleDBService peopleDBService;
    private final UsernameValidation usernameValidation;
    private final EmailValidation emailValidation;
    private final PasswordConfirmationValidation passwordConfirmationValidation;
    private final KafkaProducerService kafkaProducerService;

    public ResponseEntity<ResponseWrapper> register(PersonRegisterReqst request,
        BindingResult bindingResult) {

        validate(request, bindingResult);
        var person = createEntityByRequest(request);

        peopleDBService.save(person);
        kafkaProducerService.produce(Topics.CREATE_NEW_ACCOUNT_TOPIC, person.getUsername());

        var access = jwtService.generateAccessToken(new PersonDetails(person));
        var refresh = jwtService.generateRefreshToken(new PersonDetails(person));

        return response(Constants.REGISTRATION_SUCCESS_MESSAGE, access, refresh, person);
    }

    public ResponseEntity<ResponseWrapper> authenticate(PersonAuthReqst request,
        BindingResult bindingResult) {

        validateResults(bindingResult);
        var person = extractEntityFromDB(request.getUsername());

        authenticate(request.getUsername(), request.getPassword());

        var access = jwtService.generateAccessToken(new PersonDetails(person));
        var refresh = jwtService.generateRefreshToken(new PersonDetails(person));

        return response(Constants.AUTHENTICATION_SUCCESS_MESSAGE, access, refresh, person);
    }

    public ResponseEntity<ResponseWrapper> authenticateByRefreshToken(RefreshTokenReqst request) {

        String refresh = request.getToken();

        if (!isTokenValid(refresh)) {
            throw new TokenNotValidException();
        }

        String username = jwtService.extractUsername(refresh);
        var person = extractEntityFromDB(username);
        var access = jwtService.generateAccessToken(new PersonDetails(person));

        return response(Constants.AUTHENTICATION_SUCCESS_MESSAGE, access, refresh, person);
    }

    public ResponseEntity<Boolean> validateToken(TokenReqst token) {
        boolean isValid = isTokenValid(token.getToken());
        return ResponseEntity.ok(isValid);
    }

    public ResponseEntity<Credentials> validateToken(String token) {
        boolean isValid = isTokenValid(token);
        return isValid ? ResponseEntity.ok()
            .body(Credentials.builder().username(jwtService.extractUsername(token)).build())
            : ResponseEntity.ok(Credentials.builder().build());
    }

    private boolean isTokenValid(String token) {
        return jwtService.validateJwt(token);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            throw new NotAuthenticateException(NOT_AUTHENTICATE_MESSAGE);
        }
    }

    private void validate(PersonRegisterReqst request, BindingResult bindingResult) {
        usernameValidation.validate(request, bindingResult);
        emailValidation.validate(request, bindingResult);
        passwordConfirmationValidation.validate(request, bindingResult);
        validateResults(bindingResult);
    }

    private Person createEntityByRequest(PersonRegisterReqst request) {
        return Person.builder().username(request.getUsername()).email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
    }

    private Person extractEntityFromDB(String username) {
        return peopleDBService.getByUsername(username).orElseThrow(
            () -> new NotFoundException(
                "user with username '" + username + "' not found"));
    }

    private ResponseEntity<ResponseWrapper> response(String message, String access,
        String refresh, Person person) {
        return ResponseEntity.ok(
            ResponseWrapper.builder().status(Status.SUCCESS).message(message).time(LocalDateTime.now())
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
                            Credentials.builder().username(person.getUsername())
                                .email(person.getEmail())
                                .confirm(person.isConfirm())
                                .assay(person.isAssay())
                                .build()
                        ).build()
                    )).build()
        );
    }

    private LocalDateTime extractExpDateFromToken(String token) {
        try {
            return toLocalDateTime(jwtService.extractExpirationDateFromToken(token));
        } catch (JWTDecodeException e) {
            throw new TokenNotValidException(e.getMessage());
        }
    }

    private LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
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
}