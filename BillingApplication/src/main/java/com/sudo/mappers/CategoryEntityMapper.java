package com.sudo.mappers;

import com.sudo.entity.CategoryEntity;
import com.sudo.io.CategoryResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEntityMapper {
    CategoryEntity toEntity(CategoryResponse categoryResponse);

    CategoryResponse toDto(CategoryEntity categoryEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CategoryEntity partialUpdate(CategoryResponse categoryResponse, @MappingTarget CategoryEntity categoryEntity);
}