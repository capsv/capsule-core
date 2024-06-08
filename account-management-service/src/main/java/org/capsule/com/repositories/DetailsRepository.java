package org.capsule.com.repositories;

import java.util.Optional;
import org.capsule.com.models.Details;
import org.capsule.com.repositories.common.CustomRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepository extends CustomRepository<Details> {

    Optional<Details> findByUsername(String username);
}
