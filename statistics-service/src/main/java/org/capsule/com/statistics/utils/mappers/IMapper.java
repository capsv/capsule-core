package org.capsule.com.statistics.utils.mappers;

import org.capsule.com.statistics.dtos.CommonDTO;
import org.capsule.com.statistics.models.CEntity;

public interface IMapper<E extends CEntity, I extends CommonDTO>{

    I toDto(E entity);

    E toEntity(I dto);
}
