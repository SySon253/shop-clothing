package vn.com.shop.dto.role;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

@Getter
@Setter
public class RoleResponseDTO extends BaseResponseDTO {
    private String name;
    private String description;
}