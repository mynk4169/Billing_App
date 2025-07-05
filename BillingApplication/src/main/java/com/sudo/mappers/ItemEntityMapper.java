package com.sudo.mappers;

import com.sudo.entity.ItemEntity;
import com.sudo.io.ItemResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemEntityMapper {
    ItemEntity toEntity(ItemResponse itemResponse);

    ItemResponse toDto(ItemEntity itemEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemEntity partialUpdate(ItemResponse itemResponse, @MappingTarget ItemEntity itemEntity);
}