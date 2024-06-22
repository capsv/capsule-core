package org.capsule.com.statistics.services;

import lombok.RequiredArgsConstructor;
import org.capsule.com.statistics.models.Statistic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsDBService statisticsDBService;

    public void createNewAccount(String username) {
        Statistic statistic = new Statistic(username, 0, 0, 0);
        statisticsDBService.create(statistic);
    }

    public void deleteAccount(String username) {
        statisticsDBService.deleteByUsername(username);
    }
}
