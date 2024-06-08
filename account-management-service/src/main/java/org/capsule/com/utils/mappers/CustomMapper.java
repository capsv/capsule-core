package org.capsule.com.utils.mappers;

import org.capsule.com.dtos.common.CommonDTO;
import org.capsule.com.models.common.CEntity;

public interface CustomMapper<E extends CEntity, DTO extends CommonDTO> {

    DTO toDTO(E entity);

    E toEntity(DTO dto);
}
