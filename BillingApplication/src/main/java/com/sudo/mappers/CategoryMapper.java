package com.sudo.mappers;

import com.sudo.entity.CategoryEntity;
import com.sudo.io.CategoryRequest;
import com.sudo.io.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper
{
    CategoryEntity toEntity(CategoryRequest request);
    CategoryResponse toResponse(CategoryEntity entity);
}
