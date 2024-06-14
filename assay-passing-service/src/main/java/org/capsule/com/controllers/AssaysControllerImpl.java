package org.capsule.com.controllers;

import lombok.RequiredArgsConstructor;
import org.capsule.com.dtos.AssayReqst;
import org.capsule.com.dtos.RatingInfoResp;
import org.capsule.com.dtos.Wrapper;
import org.capsule.com.services.AssaysService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assays")
@RequiredArgsConstructor
public class AssaysControllerImpl implements IAssaysController{

    private final AssaysService assaysService;

    @Override
    public ResponseEntity<Wrapper<RatingInfoResp>> passAnAssay(AssayReqst assayReqst,
        BindingResult bindingResult) {
        return assaysService.pass(assayReqst, bindingResult);
    }
}
