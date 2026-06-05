package vn.com.shop.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private String fullName;
    private String phone;
    private Boolean enabled;
    private String lastModifiedBy;
}
