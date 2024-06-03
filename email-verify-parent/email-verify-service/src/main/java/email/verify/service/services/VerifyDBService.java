package email.verify.service.services;

import email.verify.service.models.Verify;
import email.verify.service.repositories.VerifyRepository;
import email.verify.service.utils.exceptions.NotFoundException;
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

    public Verify findByUsername(String username) {
        return verifyRepository.findByUsername(username).stream()
            .min((v1, v2) -> v2.getCreatedAt().compareTo(v1.getCreatedAt()))
            .orElseThrow(() -> new NotFoundException("no verification found for: " + username));
    }

    @Transactional(readOnly = false)
    public void deleteAllByUsername(String username) {
        verifyRepository.deleteAllByUsername(username);
    }
}
