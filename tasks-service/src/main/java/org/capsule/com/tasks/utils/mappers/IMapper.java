package org.capsule.com.tasks.utils.mappers;

import org.capsule.com.tasks.dtos.CoDto;
import org.capsule.com.tasks.models.CEntity;

public interface IMapper<E extends CEntity, C extends CoDto> {

    C toDto(E entity);

    E toEntity(C dto);
}
