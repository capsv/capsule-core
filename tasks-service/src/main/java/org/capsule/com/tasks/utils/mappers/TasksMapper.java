package org.capsule.com.tasks.utils.mappers;

import org.capsule.com.tasks.dtos.responses.TaskDto;
import org.capsule.com.tasks.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TasksMapper extends IMapper<Task, TaskDto> {

}