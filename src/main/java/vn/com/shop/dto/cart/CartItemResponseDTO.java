package vn.com.shop.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemResponseDTO {
    private Long id;
    private Long variantId;
    private String productName;
    private String sku;
    private String size;
    private String color;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
}