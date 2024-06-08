package profile.service.dev.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import profile.service.dev.models.Details;
import profile.service.dev.repositories.DetailsRepository;

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
}
