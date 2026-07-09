package vn.com.shop.service;

import org.springframework.data.domain.Pageable;
import vn.com.shop.dto.request.UserRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.dto.user.UserCreateDTO;
import vn.com.shop.dto.user.UserResponseDTO;
import vn.com.shop.dto.user.UserUpdateDTO;

import java.util.List;

public interface IUserService {
    ResponsePage<UserResponseDTO> getAllUsers(UserRequestFilter userRequestFilter, Pageable pageable);
    List<UserResponseDTO> getUserByName(String username);
    UserResponseDTO createUser(UserCreateDTO userCreateDTO);
    UserResponseDTO updateUser(Long userId, UserUpdateDTO userUpdateDTO);
    void deleteUser(Long userId);
}
