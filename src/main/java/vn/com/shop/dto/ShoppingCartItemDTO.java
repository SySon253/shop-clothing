package vn.com.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingCartItemDTO {
    private Long id;

    private Long cartId;

    private Long productItemId;

    private Integer qty;

    private BigDecimal priceAtTime;
}
