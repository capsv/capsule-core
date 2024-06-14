package org.capsule.com.repositories;

import java.util.Optional;
import org.capsule.com.models.Details;
import org.capsule.com.repositories.common.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepository extends CustomRepository<Details> {

    Optional<Details> findByUsername(String username);

    @Modifying
    @Query(value = """
        DELETE FROM account.details WHERE username = :username
        """, nativeQuery = true)
    void deleteByUsername(@Param("username") String username);
}
