package vn.com.shop.dto.product;

import lombok.Data;

@Data
public class ProductImageDTO {
    private Long id;
    private Long productId;
    private String imageUrl;
}
