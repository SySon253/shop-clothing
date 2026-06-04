package vn.com.shop.dto.product;

import lombok.Data;

@Data
public class ProductVariantRequestDTO {
    private Long productId;

    private String sku;

    private Double price;

    private Double discountPrice;

    private Integer stock;

    private Integer reservedStock;

    private String size;

    private String color;
}
