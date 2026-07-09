package vn.com.shop.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartItemRequestDTO {
    private Long variantId;
    private Integer quantity;
}
