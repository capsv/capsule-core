package dev.copavor.com.services;

import dev.copavor.com.common.Role;
import dev.copavor.com.common.Status;
import dev.copavor.com.dtos.CommonDTO;
import dev.copavor.com.dtos.requests.PersonAuthReqst;
import dev.copavor.com.dtos.requests.PersonRegisterReqst;
import dev.copavor.com.dtos.requests.TokenReqst;
import dev.copavor.com.dtos.responses.AuthResp;
import dev.copavor.com.dtos.responses.NakedPersonDTO;
import dev.copavor.com.dtos.responses.RespWrapper;
import dev.copavor.com.dtos.responses.errors.FieldError;
import dev.copavor.com.models.Person;
import dev.copavor.com.security.JwtService;
import dev.copavor.com.security.PersonDetails;
import dev.copavor.com.utils.exceptions.NotFoundException;
import dev.copavor.com.utils.exceptions.NotValidException;
import dev.copavor.com.utils.validations.PersonEmailValidation;
import dev.copavor.com.utils.validations.PersonUsernameValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PeopleDBService service;
    private final PersonUsernameValidation usernameValidation;
    private final PersonEmailValidation emailValidation;

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
