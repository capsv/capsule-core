package org.capsule.com.utils.mappers;

import org.capsule.com.dtos.responses.Data;
import org.capsule.com.models.Details;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DetailsMapper extends CustomMapper<Details, Data> {

    @Override
    Data toDTO(Details entity);

    @Override
    Details toEntity(Data dto);
}