package com.refeisoft.api.mapper;

import com.refeisoft.api.dto.UpdateUserRequestDTO;
import com.refeisoft.api.dto.UserRequestDTO;
import com.refeisoft.api.dto.UserResponseDTO;
import com.refeisoft.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "password", target = "password", ignore = true)
    User toUserEntity(UserRequestDTO requestDTO);

    UserResponseDTO toUserResponseDTO(User user);

    void toUpdateUser(UpdateUserRequestDTO requestDTO, @MappingTarget User user);
}
