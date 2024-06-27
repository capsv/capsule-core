package org.capsule.com.tasks.dtos.responses;

import org.capsule.com.tasks.dtos.CoDto;

public record UpdateStatisticDto(String status, String username) implements CoDto {

}