package vn.com.shop.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductItemDTO {
    private Long id;
    private Long productId;
    private String sku;
    private Integer qtyInStock;
    private Integer reservedStock;
    private BigDecimal price;
    private String productImage;
}
