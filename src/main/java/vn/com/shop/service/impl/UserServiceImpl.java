package vn.com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.dto.request.UserRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.dto.user.UserCreateDTO;
import vn.com.shop.dto.user.UserResponseDTO;
import vn.com.shop.dto.user.UserUpdateDTO;
import vn.com.shop.entity.UserEntity;
import vn.com.shop.mapper.UserMapper;
import vn.com.shop.repository.UserRepository;
import vn.com.shop.service.IUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponsePage<List<UserResponseDTO>> getAllUsers(UserRequestFilter userRequestFilter, Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        List<UserEntity> users = page.getContent();
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        for (UserEntity user : users) {
            UserResponseDTO userResponseDTO = userMapper.entityToDto(user);
            userResponseDTOs.add(userResponseDTO);
        }
        ResponsePage<List<UserResponseDTO>> responsePage = new ResponsePage<>();

        responsePage.setContent(userResponseDTOs);
        responsePage.setPageNumber(pageable.getPageNumber());
        responsePage.setPageSize(pageable.getPageSize());
        responsePage.setTotalElements(page.getTotalElements());
        responsePage.setTotalPages(page.getTotalPages());
        return responsePage;
    }

    @Override
    public List<UserResponseDTO> getUserByName(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username cannot be null or empty");
        }
        List<UserEntity> users = userRepository.findByUsername(username);
        return users.stream().map(userMapper::entityToDto).toList();
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByUsername(userCreateDTO.getUsername())){ throw new RuntimeException("Username already exists"); }
        if (userRepository.existsByEmail(userCreateDTO.getEmail())){ throw new RuntimeException("Email already exists"); }
        UserEntity user = new UserEntity();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());
        user.setEmail(userCreateDTO.getEmail());
        user.setFullName(userCreateDTO.getFullName());
        user.setPhone(userCreateDTO.getPhone());
        user.setCreatedBy(userCreateDTO.getCreateBy());
        user.setLastModifiedBy(userCreateDTO.getLastModifiedBy());
        user.setEnabled(true);
        userRepository.save(user);
        return null;
    }

    @Override
    public UserResponseDTO updateUser(Long userId,UserUpdateDTO userUpdateDTO) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (userUpdateDTO.getFullName() != null) {user.setFullName(userUpdateDTO.getFullName());}
        if (userUpdateDTO.getPhone() != null) {user.setPhone(userUpdateDTO.getPhone());}
        if (userUpdateDTO.getEnabled() != null) {user.setEnabled(userUpdateDTO.getEnabled());}
        if (userUpdateDTO.getLastModifiedBy() != null) {user.setLastModifiedBy(userUpdateDTO.getLastModifiedBy());}
        user = userRepository.save(user);
        return userMapper.entityToDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }
}
