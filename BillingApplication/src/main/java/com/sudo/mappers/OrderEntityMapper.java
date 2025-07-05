package com.sudo.mappers;

import com.sudo.entity.OrderEntity;
import com.sudo.io.OrderResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface
OrderEntityMapper {
    OrderEntity toEntity(OrderResponse orderResponse);

    OrderResponse toDto(OrderEntity orderEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderEntity partialUpdate(OrderResponse orderResponse, @MappingTarget OrderEntity orderEntity);
}