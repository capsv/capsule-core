package org.capsule.com.statistics.controllers;

import lombok.RequiredArgsConstructor;
import org.capsule.com.statistics.dtos.StatisticDTOResp;
import org.capsule.com.statistics.services.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsControllerImpl implements IStatisticsController {

    private final StatisticsService statisticsService;

    @Override
    public ResponseEntity<StatisticDTOResp> getStatistics(String token) {
        return statisticsService.getStatistics(token);
    }
}
