package auth.service.dev.services;

import auth.service.dev.dtos.requests.PersonAuthReqst;
import auth.service.dev.dtos.requests.PersonRegisterReqst;
import auth.service.dev.dtos.responses.AuthResp;
import auth.service.dev.dtos.responses.NakedPersonDTO;
import auth.service.dev.dtos.responses.RespWrapper;
import auth.service.dev.utils.exceptions.NotFoundException;
import auth.service.dev.utils.exceptions.NotValidException;
import auth.service.dev.utils.validations.PersonEmailValidation;
import auth.service.dev.utils.validations.PersonUsernameValidation;
import auth.service.dev.common.Role;
import auth.service.dev.common.Status;
import auth.service.dev.dtos.requests.TokenReqst;
import auth.service.dev.dtos.responses.errors.FieldError;
import auth.service.dev.models.Person;
import auth.service.dev.security.JwtService;
import auth.service.dev.security.PersonDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PeopleDBService service;
    private final PersonUsernameValidation usernameValidation;
    private final PersonEmailValidation emailValidation;

    public AuthService(JwtService jwtService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       PeopleDBService service,
                       PersonUsernameValidation usernameValidation,
                       PersonEmailValidation emailValidation) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.service = service;
        this.usernameValidation = usernameValidation;
        this.emailValidation = emailValidation;
    }

    public ResponseEntity<RespWrapper> register(
            PersonRegisterReqst reqst, BindingResult bindingResult){

        usernameValidation.validate(reqst,bindingResult);
        emailValidation.validate(reqst,bindingResult);
        validate(bindingResult);

        var person = Person.builder()
                .username(reqst.getUsername())
                .email(reqst.getEmail())
                .password(passwordEncoder.encode(reqst.getPassword()))
                .role(Role.USER)
                .build();

        service.save(person);
        var token=jwtService.generateToken(new PersonDetails(person));

        return ResponseEntity.ok(
                RespWrapper.builder()
                        .status(Status.SUCCESS)
                        .message("Registration successful")
                        .time(LocalDateTime.now())
                        .payload(List.of(
                                AuthResp.builder()
                                        .token(token)
                                        .iat(LocalDateTime.now())
                                        .exp( toLocalDateTime(
                                                jwtService.extractExpirationDateFromToken(token)
                                        ))
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

    public ResponseEntity<RespWrapper> authenticate(
            PersonAuthReqst reqst, BindingResult bindingResult){

        validate(bindingResult);

        var person=service.getByUsername(reqst.getUsername())
                .orElseThrow(()->new NotFoundException("user with username: "+reqst.getUsername()+" not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(reqst.getUsername(),reqst.getPassword())
        );

        var token=jwtService.generateToken(new PersonDetails(person));

        return ResponseEntity.ok(
                RespWrapper.builder()
                        .status(Status.SUCCESS)
                        .message("Authentication successful")
                        .time(LocalDateTime.now())
                        .payload(
                                List.of(
                                        AuthResp.builder()
                                                .token(token)
                                                .iat(LocalDateTime.now())
                                                .exp( toLocalDateTime(
                                                        jwtService.extractExpirationDateFromToken(token)
                                                ))
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

    public ResponseEntity<Boolean> validateToken(TokenReqst token) {
        boolean isValid = jwtService.validateJwt(token.getToken());
        return ResponseEntity.ok(isValid);
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

    public LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
