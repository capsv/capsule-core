package org.capsule.com.tasks.dtos.responses;

import org.capsule.com.tasks.dtos.CoDto;

public record TaskDto(long id, String title, String description) implements CoDto {

}
