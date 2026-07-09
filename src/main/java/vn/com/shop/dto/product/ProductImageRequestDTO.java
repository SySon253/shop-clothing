package vn.com.shop.dto.product;

import lombok.Data;

@Data
public class ProductImageRequestDTO {
    private Long productId;
    private String imageUrl;
    private Boolean thumbnail;
}
