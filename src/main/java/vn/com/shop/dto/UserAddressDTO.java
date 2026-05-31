package vn.com.shop.dto;

import lombok.Data;

@Data
public class UserAddressDTO {
    private Long userId;

    private Long addressId;

    private boolean isDefault;
}
