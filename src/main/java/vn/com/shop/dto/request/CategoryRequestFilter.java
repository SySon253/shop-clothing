package vn.com.shop.dto.request;

import lombok.Data;

@Data
public class CategoryRequestFilter {
    private String keyword;
    private Long parentId;
    private Boolean deleted = false;
}
