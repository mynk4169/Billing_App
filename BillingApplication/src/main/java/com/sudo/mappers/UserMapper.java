package com.sudo.mappers;

import com.sudo.entity.UserEntity;
import com.sudo.io.UserRequest;
import com.sudo.io.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel="spring",unmappedTargetPolicy= ReportingPolicy.IGNORE)
public interface UserMapper {
    UserEntity toEntity(UserRequest request);
    UserResponse toResponse(UserEntity user);
}

