package org.capsule.com.tasks.utils.mappers;

import org.capsule.com.tasks.dtos.responses.TaskDto;
import org.capsule.com.tasks.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TasksMapper extends IMapper<Task, TaskDto> {

    @Override
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "title", target = "title"),
        @Mapping(source = "description", target = "description"),
    })
    TaskDto toDto(Task entity);
}