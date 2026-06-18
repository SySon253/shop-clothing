package vn.com.shop.dto.product;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

import java.math.BigDecimal;


@Getter
@Setter
public class ProductVariantResponseDTO extends BaseResponseDTO {
    private String sku;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stock;
    private Integer reservedStock;
    private String size;
    private String color;
}