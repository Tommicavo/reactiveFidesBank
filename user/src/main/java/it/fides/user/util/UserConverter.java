package it.fides.user.util;

import it.fides.user.models.dtos.RoleDto;
import it.fides.user.models.dtos.UserDto;
import it.fides.user.models.entities.RoleEntity;
import it.fides.user.models.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto toDto(UserEntity userEntity, RoleEntity roleEntity) {

        RoleDto roleDto = new RoleDto();
        roleDto.setIdRole(roleEntity.getIdRole());
        roleDto.setLabelRole(roleEntity.getLabelRole());

        UserDto userDto = new UserDto();
        userDto.setIdUser(userEntity.getIdUser());
        userDto.setFirstNameUser(userEntity.getFirstNameUser());
        userDto.setLastNameUser(userEntity.getLastNameUser());
        userDto.setEmailUser(userEntity.getEmailUser());
        userDto.setPasswordUser(userEntity.getPasswordUser());
        userDto.setRole(roleDto);

        return userDto;
    }

    public UserEntity toEntity(UserDto userDto) {

        UserEntity userEntity = new UserEntity();
        if (userDto.getIdUser() != null) {
            userEntity.setIdUser(userDto.getIdUser());
        }
        userEntity.setFirstNameUser(userDto.getFirstNameUser());
        userEntity.setLastNameUser(userDto.getLastNameUser());
        userEntity.setEmailUser(userDto.getEmailUser());
        userEntity.setPasswordUser(userDto.getPasswordUser());
        userEntity.setIdRole(userDto.getRole().getIdRole());

        return userEntity;
    }
}
