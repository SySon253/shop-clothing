package vn.com.shop.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String createBy;
    private String lastModifiedBy;
}