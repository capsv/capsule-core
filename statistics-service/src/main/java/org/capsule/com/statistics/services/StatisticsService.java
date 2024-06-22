package org.capsule.com.statistics.services;

import lombok.RequiredArgsConstructor;
import org.capsule.com.statistics.dtos.Score;
import org.capsule.com.statistics.models.Statistic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsDBService statisticsDBService;

    public void createNewAccount(String username) {
        Statistic statistic = new Statistic(username, 0, 0, 0);
        statisticsDBService.save(statistic);
    }

    public void deleteAccount(String username) {
        statisticsDBService.deleteByUsername(username);
    }

    public void setScore(Score payload) {
        Statistic statistic = statisticsDBService.findByUsername(payload.username());
        statistic.setScore(payload.score());
        statisticsDBService.save(statistic);
    }
}
