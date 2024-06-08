package org.capsule.com.utils.mappers;

import org.capsule.com.dtos.common.CommonDTO;
import org.capsule.com.models.common.Entity;

public interface CustomMapper<E extends Entity, DTO extends CommonDTO> {

    DTO toDTO(E entity);

    E toEntity(DTO dto);
}
