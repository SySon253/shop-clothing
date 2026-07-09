package vn.com.shop.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponseDTO {
    private Long id;
    private Long userId;
    private Integer totalItems;
    private Double totalPrice;
    private List<CartItemResponseDTO> items;
}