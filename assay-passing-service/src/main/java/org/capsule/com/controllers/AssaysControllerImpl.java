package org.capsule.com.controllers;

import org.capsule.com.dtos.AssayReqst;
import org.capsule.com.dtos.RatingInfoResp;
import org.capsule.com.dtos.Wrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assays")
public class AssaysControllerImpl implements IAssaysController{

    @Override
    public ResponseEntity<Wrapper<RatingInfoResp>> passAnAssay(AssayReqst assayReqst,
        BindingResult bindingResult) {
        return null;
    }
}
