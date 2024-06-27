package org.capsule.com.controllers;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.capsule.com.controllers.interfaces.IDetailsController;
import org.capsule.com.dtos.responses.Data;
import org.capsule.com.dtos.responses.Wrapper;
import org.capsule.com.services.DetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class PeopleControllerImpl implements IDetailsController {

    private final DetailsService detailsService;

    @Override
    public ResponseEntity<Wrapper<Data>> get(String token) {
        return detailsService.get(token);
    }

    @Override
    public ResponseEntity<Wrapper<Data>> partiallyUpdate(String token, Map<String, Object> updates) {
        return detailsService.partiallyUpdate(token, updates);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(String token) {
        return detailsService.delete(token);
    }
}