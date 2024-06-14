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
    public ResponseEntity<Wrapper<Data>> get(String username) {
        return detailsService.get(username);
    }

    @Override
    public ResponseEntity<Wrapper<Data>> partiallyUpdate(String username, Map<String, Object> updates) {
        return detailsService.partiallyUpdate(username, updates);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(String username) {
        return detailsService.delete(username);
    }
}