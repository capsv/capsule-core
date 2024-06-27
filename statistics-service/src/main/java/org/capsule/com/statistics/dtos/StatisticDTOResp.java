package org.capsule.com.statistics.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StatisticDTOResp(@JsonProperty("score") int score,
                               @JsonProperty("completedTasks") int completedTasks,
                               @JsonProperty("missedTasks") int missedTasks) implements CommonDTO {

}
