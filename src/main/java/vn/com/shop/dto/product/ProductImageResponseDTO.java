package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

@Getter
@Setter
public class ProductImageResponseDTO extends BaseResponseDTO {
    private String imageUrl;
    private Boolean thumbnail;
}