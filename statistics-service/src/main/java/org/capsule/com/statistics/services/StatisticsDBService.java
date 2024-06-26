package org.capsule.com.statistics.services;

import lombok.RequiredArgsConstructor;
import org.capsule.com.statistics.models.Statistic;
import org.capsule.com.statistics.repositories.StatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsDBService {

    private final StatisticsRepository statisticsRepository;

    @Transactional(readOnly = false)
    public void save(Statistic statistic) {
        statisticsRepository.save(statistic);
    }

    @Transactional(readOnly = false)
    public void deleteByUsername(String username) {
        statisticsRepository.deleteByUsername(username);
    }

    public Statistic findByUsername(String username) {
        return statisticsRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

    @Transactional(readOnly = false)
    public void incrementCompleted(String username) {
        Statistic statistic = findByUsername(username);
        statistic.setCompletedTasks(statistic.getCompletedTasks() + 1);
        save(statistic);
    }

    @Transactional(readOnly = false)
    public void incrementSkipped(String username) {
        Statistic statistic = findByUsername(username);
        statistic.setMissedTasks(statistic.getMissedTasks() + 1);
        save(statistic);
    }
}
