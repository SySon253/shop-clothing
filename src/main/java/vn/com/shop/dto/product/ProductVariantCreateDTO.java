package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductVariantCreateDTO {

    private String sku;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stock;
    private Integer reservedStock;
    private String size;
    private String color;
}
