package vn.com.shop.controller.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.request.UserRequestFilter;
import vn.com.shop.dto.response.ResponsePage;
import vn.com.shop.dto.user.UserCreateDTO;
import vn.com.shop.dto.user.UserResponseDTO;
import vn.com.shop.dto.user.UserUpdateDTO;
import vn.com.shop.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserResourceController {
    private final IUserService userService;

    @PostMapping("/all-user")
    public ResponseEntity<ResponsePage<UserResponseDTO>> getAllUser(
            @RequestBody(required = false) UserRequestFilter userRequestFilter,
            Pageable pageable
            ) {
        if (userRequestFilter == null) {
            userRequestFilter = new UserRequestFilter();
        }
        return ResponseEntity.ok(userService.getAllUsers(userRequestFilter,pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> getUser(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByName(username));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
