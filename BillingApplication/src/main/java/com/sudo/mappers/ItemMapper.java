package com.sudo.mappers;

import com.sudo.entity.ItemEntity;
import com.sudo.io.ItemRequest;
import com.sudo.io.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper( componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper
{
    
    ItemEntity toEntity(ItemRequest request);

    ItemResponse toResponse(ItemEntity item);
    @Named("generateUUID")
    default String generateUUID()
    {
        return UUID.randomUUID().toString();
    }
}
