package org.capsule.com.tasks.dtos.responses;

import java.util.List;
import org.capsule.com.tasks.dtos.CoDto;

public record ListOfTasksResp(List<TaskDto> payload) implements CoDto {

}
