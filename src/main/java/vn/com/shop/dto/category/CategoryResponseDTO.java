package vn.com.shop.dto.category;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

@Getter
@Setter
public class CategoryResponseDTO extends BaseResponseDTO {
    private String name;
    private String slug;
    private Long parentId;
}