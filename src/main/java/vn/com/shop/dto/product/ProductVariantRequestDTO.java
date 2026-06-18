package vn.com.shop.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVariantRequestDTO {
    private Long productId;

    private String sku;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer stock;

    private Integer reservedStock;

    private String size;

    private String color;
}
