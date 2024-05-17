package auth.service.dev.services;

import auth.service.dev.dtos.requests.PersonAuthReqst;
import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.dtos.requests.RefreshTokenReqst;
import auth.service.dev.dtos.responses.entities.Credentials;
import auth.service.dev.dtos.responses.tokens.*;
import auth.service.dev.security.PersonDetailsService;
import auth.service.dev.utils.exceptions.NotFoundException;
import auth.service.dev.utils.exceptions.NotValidException;
import auth.service.dev.utils.exceptions.TokenNotValidException;
import auth.service.dev.utils.validations.PersonEmailValidation;
import auth.service.dev.utils.validations.PersonUsernameValidation;
import auth.service.dev.common.Role;
import auth.service.dev.common.Status;
import auth.service.dev.dtos.requests.TokenReqst;
import auth.service.dev.dtos.responses.errors.FieldError;
import auth.service.dev.models.Person;
import auth.service.dev.security.JwtService;
import auth.service.dev.security.PersonDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PeopleDBService service;
    private final PersonUsernameValidation usernameValidation;
    private final PersonEmailValidation emailValidation;
    private final PersonDetailsService personDetailsService;

    public AuthService(JwtService jwtService, PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager, PeopleDBService service,
        PersonUsernameValidation usernameValidation, PersonEmailValidation emailValidation,
        PersonDetailsService personDetailsService) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.service = service;
        this.usernameValidation = usernameValidation;
        this.emailValidation = emailValidation;
        this.personDetailsService = personDetailsService;
    }

    public ResponseEntity<RespWrapper> register(
            PersonRegisterReqst reqst, BindingResult bindingResult){

        usernameValidation.validate(reqst,bindingResult);
        emailValidation.validate(reqst,bindingResult);

        if(!reqst.getPassword().equals(reqst.getConfirmationPassword())){
            bindingResult.rejectValue("confirmationPassword","",
                "the confirmation password must match the password");
        }

        validate(bindingResult);

        var person = Person.builder()
                .username(reqst.getUsername())
                .email(reqst.getEmail())
                .password(passwordEncoder.encode(reqst.getPassword()))
                .role(Role.USER)
                .build();

        service.save(person);
        var accessToken=jwtService.generateToken(new PersonDetails(person));
        var refreshToken=jwtService.generateRefreshToken(new PersonDetails(person));

        return ResponseEntity.ok(
                RespWrapper.builder()
                        .status(Status.SUCCESS)
                        .message("Registration successful")
                        .time(LocalDateTime.now())
                        .payload(List.of(
                                RegistrationResp.builder()
                                        .accessToken(
                                                AccessToken.builder()
                                                        .token(accessToken)
                                                        .iat(LocalDateTime.now())
                                                        .exp( toLocalDateTime(
                                                                jwtService.extractExpirationDateFromToken(accessToken)
                                                        )).build()
                                        )
                                        .refreshToken(
                                                RefreshToken.builder()
                                                        .token(refreshToken)
                                                        .iat(LocalDateTime.now())
                                                        .exp( toLocalDateTime(
                                                                jwtService.extractExpirationDateFromToken(refreshToken)
                                                        )).build()
                                        )
                                        .user(
                                                NakedPersonDTO.builder()
                                                        .id(
                                                                service.getByUsername(person.getUsername()).get().getId()
                                                        )
                                                        .username(person.getUsername())
                                                        .email(person.getEmail())
                                                        .build()
                                        ).build()
                                )
                        ).build()
        );
    }

    public ResponseEntity<RespWrapper> authenticateByRefreshToken(RefreshTokenReqst reqst){

        String refreshToken=reqst.getToken();

        if(!jwtService.validateJwt(refreshToken)){
            log.info("TOKEN IS NOT VALID");
            throw new TokenNotValidException();
        }

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = personDetailsService.loadUserByUsername(username);
        var person=service.getByUsername(username)
                .orElseThrow(()->new NotFoundException("user with username: "+username+" not found"));

        PersonDetails personDetails=(PersonDetails) userDetails;
        log.info("AUTHENTICATE BY REFRESH TOKEN: {}",personDetails.toString());

        log.info("ALL GOOD -> STARTING GENERATING ACCESS TOKEN");
        var accessToken=jwtService.generateToken(userDetails);

        return getRespWrapperResponseEntity(person, accessToken);
    }


    public ResponseEntity<RespWrapper> authenticate(
            PersonAuthReqst reqst, BindingResult bindingResult){

        validate(bindingResult);

        var person=service.getByUsername(reqst.getUsername())
                .orElseThrow(()->new NotFoundException("user with username '"+reqst.getUsername()+"' not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(reqst.getUsername(),reqst.getPassword())
        );

        var accessToken=jwtService.generateToken(new PersonDetails(person));

        return getRespWrapperResponseEntity(person, accessToken);
    }

    public ResponseEntity<Boolean> validateToken(TokenReqst token) {
        boolean isValid = jwtService.validateJwt(token.getToken());
        log.info(isValid ? "VALID" : "INVALID");
        return ResponseEntity.ok(isValid);
    }

    public ResponseEntity<Credentials> validateToken(String token) {
        boolean isValid = jwtService.validateJwt(token);
        return isValid ? ResponseEntity.ok().body(
            Credentials.builder().username(jwtService.extractUsername(token)).build()
        ) : ResponseEntity.ok(Credentials.builder().build());
    }

    private void validate(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors=new ArrayList<>();
            List<org.springframework.validation.FieldError> fieldErrors=bindingResult.getFieldErrors();

            for (org.springframework.validation.FieldError error : fieldErrors)
                errors.add(new FieldError(error.getField(),error.getDefaultMessage()));

            throw new NotValidException(errors);
        }
    }

    private LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private ResponseEntity<RespWrapper> getRespWrapperResponseEntity(Person person, String accessToken) {
        return ResponseEntity.ok(
                RespWrapper.builder()
                        .status(Status.SUCCESS)
                        .message("Authentication successful")
                        .time(LocalDateTime.now())
                        .payload(
                                List.of(
                                        AuthResp.builder()
                                                .accessToken(
                                                        AccessToken.builder()
                                                                .token(accessToken)
                                                                .iat(LocalDateTime.now())
                                                                .exp(toLocalDateTime(jwtService.extractExpirationDateFromToken(accessToken)))
                                                                .build()
                                                )
                                                .user(
                                                        NakedPersonDTO.builder()
                                                                .id(
                                                                        service.getByUsername(person.getUsername()).get().getId()
                                                                )
                                                                .username(person.getUsername())
                                                                .email(person.getEmail())
                                                                .build()
                                                ).build()
                                )
                        ).build()
        );
    }

}
