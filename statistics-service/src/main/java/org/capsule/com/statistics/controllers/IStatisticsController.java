package org.capsule.com.statistics.controllers;

import org.capsule.com.statistics.dtos.StatisticDTOResp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IStatisticsController {

    @GetMapping(headers = {"Authorization"}, produces = "application/json")
    ResponseEntity<StatisticDTOResp> getStatistics(@RequestHeader("Authorization") String token);
}
