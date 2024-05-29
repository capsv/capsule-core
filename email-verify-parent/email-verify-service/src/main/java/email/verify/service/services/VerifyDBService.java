package email.verify.service.services;

import email.verify.service.models.Verify;
import email.verify.service.repositories.VerifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VerifyDBService {

    private final VerifyRepository verifyRepository;

    @Transactional(readOnly = false)
    public void save(Verify verify) {
        verifyRepository.save(verify);
    }
}
