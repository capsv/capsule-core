package org.capsule.com.utils.mappers;

import org.capsule.com.dtos.kafka.Letter;
import org.capsule.com.models.Verify;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VerifyMapper {

    Letter convertToDTO(Verify verify);
}