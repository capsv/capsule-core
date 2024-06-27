package org.capsule.com.statistics.utils.mappers;

import org.capsule.com.statistics.dtos.StatisticDTOResp;
import org.capsule.com.statistics.models.Statistic;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatisticsMapper extends IMapper<Statistic, StatisticDTOResp> {

    @Override
    StatisticDTOResp toDto(Statistic entity);

    @Override
    Statistic toEntity(StatisticDTOResp dto);
}
