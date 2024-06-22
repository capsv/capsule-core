package org.capsule.com.statistics.repositories;

import java.util.Optional;
import org.capsule.com.statistics.models.Statistic;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends CommonJpaRepository<Statistic> {

    Optional<Statistic> findByUsername(String username);

    @Modifying
    @Query(value = """
        DELETE FROM statistic.statistic WHERE username = :username
        """, nativeQuery = true)
    void deleteByUsername(@Param("username") String username);
}