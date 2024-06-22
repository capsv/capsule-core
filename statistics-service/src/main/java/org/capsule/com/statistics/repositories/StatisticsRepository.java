package org.capsule.com.statistics.repositories;

import org.capsule.com.statistics.models.Statistic;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends CommonJpaRepository<Statistic> {

}