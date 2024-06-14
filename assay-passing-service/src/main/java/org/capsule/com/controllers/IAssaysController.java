package org.capsule.com.controllers;

import jakarta.validation.Valid;
import org.capsule.com.dtos.AssayReqst;
import org.capsule.com.dtos.RatingInfoResp;
import org.capsule.com.dtos.Wrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAssaysController {

    @PostMapping("/pass")
    ResponseEntity<Wrapper<RatingInfoResp>> passAnAssay(@RequestBody @Valid AssayReqst assayReqst,
        BindingResult bindingResult);
}
