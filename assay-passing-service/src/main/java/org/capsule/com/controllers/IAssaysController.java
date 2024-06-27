package org.capsule.com.controllers;

import jakarta.validation.Valid;
import org.capsule.com.dtos.AssayReqst;
import org.capsule.com.dtos.ScoreInfoResp;
import org.capsule.com.dtos.Wrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IAssaysController {

    @PostMapping(path = "/pass", headers = {"Authorization"}, consumes = "application/json",
        produces = "application/json")
    ResponseEntity<Wrapper<ScoreInfoResp>> passAnAssay(@RequestHeader("Authorization") String token,
        @RequestBody @Valid AssayReqst request, BindingResult bindingResult);
}
