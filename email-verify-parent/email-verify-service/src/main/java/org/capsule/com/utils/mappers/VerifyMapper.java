package org.capsule.com.utils.mappers;

import org.capsule.com.dtos.kafka.Letter;
import org.capsule.com.models.Verify;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VerifyMapper {

    VerifyMapper INSTANCE = Mappers.getMapper(VerifyMapper.class);

    Letter convertToDTO(Verify verify);
}