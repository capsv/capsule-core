package org.capsule.com.repositories;

import org.capsule.com.models.Verify;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyRepository extends JpaRepository<Verify, Long> {

    List<Verify> findByUsername(String username);

    void deleteAllByUsername(String username);
}