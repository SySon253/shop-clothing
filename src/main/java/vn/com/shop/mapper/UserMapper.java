package vn.com.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.shop.dto.user.UserResponseDTO;
import vn.com.shop.entity.UserEntity;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public UserResponseDTO entityToDto(UserEntity userEntity) {
        if(userEntity == null) return null;

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userEntity.getId());
        userResponseDTO.setUsername(userEntity.getUsername());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setFullName(userEntity.getFullName());
        userResponseDTO.setPhone(userEntity.getPhone());
        userResponseDTO.setEnabled(userEntity.getEnabled());
        userResponseDTO.setCreatedBy(userEntity.getCreatedBy());
        userResponseDTO.setCreatedDate(userEntity.getCreatedDate());
        userResponseDTO.setLastModifiedBy(userEntity.getLastModifiedBy());
        userResponseDTO.setLastModifiedDate(userEntity.getLastModifiedDate());
        return userResponseDTO;
    }
}
