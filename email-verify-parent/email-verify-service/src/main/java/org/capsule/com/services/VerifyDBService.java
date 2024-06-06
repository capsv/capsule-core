package org.capsule.com.services;

import org.capsule.com.models.Verify;
import org.capsule.com.repositories.VerifyRepository;
import org.capsule.com.utils.exceptions.NotFoundException;
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
