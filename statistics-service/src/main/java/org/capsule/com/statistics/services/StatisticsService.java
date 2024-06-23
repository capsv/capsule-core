package org.capsule.com.statistics.services;

import lombok.RequiredArgsConstructor;
import org.capsule.com.statistics.dtos.Score;
import org.capsule.com.statistics.dtos.StatisticDTOResp;
import org.capsule.com.statistics.models.Statistic;
import org.capsule.com.statistics.utils.mappers.StatisticsMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsDBService statisticsDBService;
    private final DecodeJwtService decodeJwtService;
    private final StatisticsMapper statisticsMapper;

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

    public ResponseEntity<StatisticDTOResp> getStatistics(String token) {
        String username = extractUsernameFromToken(token);
        Statistic statistic = statisticsDBService.findByUsername(username);
        return new ResponseEntity<>(statisticsMapper.toDto(statistic), HttpStatus.OK);
    }

    private String extractUsernameFromToken(String token) {
        try {
            return decodeJwtService.extractUsername(token.substring(7));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
