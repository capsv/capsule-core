package org.capsule.com.services;

import lombok.RequiredArgsConstructor;
import org.capsule.com.models.Details;
import org.capsule.com.repositories.DetailsRepository;
import org.capsule.com.utils.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailsDBService {

    private final Logger LOGGER = LoggerFactory.getLogger(DetailsDBService.class);
    private final DetailsRepository detailsRepository;

    @Transactional(readOnly = false)
    public void save(Details details) {
        detailsRepository.save(details);
    }

    public Details findByUsername(String username) {
        return detailsRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("there is no data for " + username, "username",
                "not found"));
    }
}
