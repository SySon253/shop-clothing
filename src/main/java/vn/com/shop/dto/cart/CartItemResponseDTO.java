package vn.com.shop.dto.cart;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemResponseDTO {
    private Long id;
    private Long variantId;
    private String productName;
    private String sku;
    private String size;
    private String color;
    private Double price;
    private Integer quantity;
    private Double subtotal;
}