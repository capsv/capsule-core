package email.verify.service.repositories;

import email.verify.service.models.Verify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyRepository extends JpaRepository<Verify, Long> {

}