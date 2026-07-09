package vn.com.shop.dto.user;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

@Getter
@Setter
public class UserResponseDTO extends BaseResponseDTO {
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private Boolean enabled;
}
