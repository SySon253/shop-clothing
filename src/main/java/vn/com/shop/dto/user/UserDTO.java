package vn.com.shop.dto.user;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    // Không nên trả password ra ngoài response
    private String password;
    private String phone;
    private String address;
    private boolean deleted;
    private String code;
    // Lưu role name thay vì cả entity
    private Set<String> roles = new HashSet<>();
}
