package vn.com.shop.dto.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateDTO {
    private String name;
    private String slug;
    private Long parentId;
}
