package vn.com.shop.dto.product;

import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Long id;
    private String categoryName;
    private Long parentCategoryId;
}
